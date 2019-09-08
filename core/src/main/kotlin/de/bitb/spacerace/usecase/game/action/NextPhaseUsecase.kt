package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.NextPhaseInfo
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.obtain.ObtainShopCommand
import de.bitb.spacerace.exceptions.DiceFirstException
import de.bitb.spacerace.exceptions.StepsLeftException
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.getPlayerPosition
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.greenrobot.eventbus.EventBus.getDefault
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val graphicController: GraphicController,
        private val fieldController: FieldController,
        private val playerController: PlayerController,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<NextPhaseInfo, PlayerColor> {

    override fun buildUseCaseSingle(params: PlayerColor): Single<NextPhaseInfo> =
            checkCurrentPlayerUsecase.buildUseCaseSingle(params)
                    .flatMap { checkEndable(it) }
                    .flatMap { doPhase(it) }
                    .flatMap { intoDb ->
                        playerDataSource.insertAllReturnAll(intoDb).map { it.first() }
                    }
                    .map { NextPhaseInfo(it, it.phase) }

    private fun checkEndable(playerData: PlayerData) =
            when (playerData.phase) {
                Phase.MAIN1 -> canEndMain1(playerData)
                Phase.MOVE -> canEndMove(playerData)
                Phase.MAIN2 -> canEndMain2(playerData)
                Phase.END_TURN -> canEndTurn(playerData)
                Phase.END_ROUND -> Single.error(UnsupportedOperationException())
            }

    private fun checkPhase(playerColor: PlayerColor, phase: Phase) =
            checkPlayerPhaseUsecase.buildUseCaseSingle(playerColor to phase)

    private fun canEndMain1(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.MAIN1)
                    .flatMap {
                        Single.create { emitter: SingleEmitter<PlayerData> ->
                            if (playerData.areStepsLeft()) {
                                emitter.onSuccess(playerData)
                            } else emitter.onError(DiceFirstException(playerData.playerColor))
                        }
                    }


    private fun canEndMove(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.MOVE)
                    .flatMap {
                        Single.create { emitter: SingleEmitter<PlayerData> ->
                            if (!playerData.areStepsLeft()) {
                                emitter.onSuccess(playerData)
                            } else emitter.onError(StepsLeftException(playerData.playerColor, playerData.stepsLeft()))
                        }
                    }

    private fun canEndMain2(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.MAIN2)

    private fun canEndTurn(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.END_TURN)


    private fun doPhase(playerData: PlayerData): Single<PlayerData> =
            playerData.apply {
                phase = Phase.next(phase)
            }.let { player ->
                when (player.phase) {
                    Phase.MOVE -> startMove(player)
                    Phase.MAIN2 -> startMain2(player)
                    Phase.END_TURN -> endTurn(player)
                    else -> Single.just(player)
                }
            }

    private fun startMove(playerData: PlayerData): Single<PlayerData> =
            Single.fromCallable {
                playerData.apply { steps.add(graphicController.getPlayerField(playerColor).gamePosition) }
            }

    private fun startMain2(playerData: PlayerData): Single<PlayerData> =
            Single.fromCallable {
                obtainField(playerData)
            }

    private fun endTurn(playerData: PlayerData): Single<PlayerData> =
            Single.fromCallable {
                playerData.also { playerController.changePlayer() }
            }

    //TODO clean me
    private fun obtainField(playerData: PlayerData): PlayerData =
            graphicController
                    .getPlayerField(playerData.playerColor)
                    .let {
                        triggerItems(it, playerData)
                        when (it.fieldType) {
                            FieldType.WIN -> obtainWin(playerData)
                            FieldType.LOSE -> obtainLose(playerData)
                            FieldType.AMBUSH -> obtainAmbush(playerData)
                            FieldType.GIFT -> obtainGift(playerData)
                            FieldType.MINE -> obtainMine(playerData)
                            FieldType.TUNNEL -> obtainTunnel(playerData)
                            FieldType.GOAL -> obtainGoal(playerData)
                            FieldType.SHOP -> getDefault().post(ObtainShopCommand(playerData)) //TODO open shop onSuccess
                            else -> Logger.println("IMPL ME")
                        }
                        Logger.println("Field ${it.fieldType.name}: $playerData")
                        playerData
                    }

    private fun triggerItems(field: SpaceField, playerData: PlayerData) {
        mutableListOf<Item>()
                .apply { addAll(field.disposedItems) }
                .forEach { it.use(playerData) }
    }

    private fun obtainGoal(playerData: PlayerData) {
        val playerPosition = graphicController.getPlayerPosition(playerData.playerColor)
        if (fieldController.currentGoal?.isPosition(playerPosition) == true) {
            playerData.apply {
                credits += GOAL_CREDITS
                victories++
            }
            fieldController.setRandomGoalPosition()
        }
    }

    private fun obtainTunnel(playerData: PlayerData) {
        val tunnel = getRandomTunnel(playerData.playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        val field = graphicController.fieldGraphics[tunnel.gamePosition] ?: NONE_FIELD
        graphicController.getPlayer(playerData.playerColor).setFieldPosition(field)
    }

    private fun getRandomTunnel(playerColor: PlayerColor): FieldData {
        val playerPosition = graphicController.getPlayerPosition(playerColor)
        val tunnelList = fieldController.fieldsMap[FieldType.TUNNEL]!!
        var tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]

        while (tunnel.gamePosition.isPosition(playerPosition)) {
            tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]
        }
        return tunnel
    }

    private fun obtainMine(playerData: PlayerData) {
        (graphicController.getPlayerField(playerData.playerColor) as MineField)
                .apply { owner = playerData.playerColor }
    }

    private fun obtainGift(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor).addRandomGift()
    }

    private fun obtainAmbush(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor)
                .attachItem(ItemCollection.SLOW_MINE.create(playerData.playerColor) as DisposableItem)
    }

    private fun obtainLose(playerData: PlayerData) {
        playerData.substractRandomWin()
    }

    private fun obtainWin(playerData: PlayerData) {
        playerData.addRandomWin()
    }

}