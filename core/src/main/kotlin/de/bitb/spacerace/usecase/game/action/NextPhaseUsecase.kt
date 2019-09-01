package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.NextPhaseInfo
import de.bitb.spacerace.controller.toConnectionInfo
import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.obtain.ObtainShopCommand
import de.bitb.spacerace.exceptions.DiceFirstException
import de.bitb.spacerace.exceptions.MoveEveryStepException
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.getPlayerPosition
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
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
        private val playerDataSource: PlayerDataSource,
        private var playerColorDispender: PlayerColorDispender
) : ResultUseCase<NextPhaseInfo, PlayerColor> {

    //TODO clean me
    override fun buildUseCaseSingle(params: PlayerColor): Single<NextPhaseInfo> =
            checkCurrentPlayerUsecase.buildUseCaseSingle(params)
                    .flatMap { playerData ->
                        when (playerData.phase) {
                            Phase.MAIN1 -> canEndMain1(playerData)
                            Phase.MOVE -> canEndMove(playerData)
                            Phase.MAIN2 -> canEndMain2(playerData)
                            Phase.END_TURN -> canEndTurn(playerData)
                            Phase.END_ROUND -> Single.error(UnsupportedOperationException())
                        }
                    }
                    .flatMap { playerData ->
                        playerData.nextPhase()
                        val phasebla = when (playerData.phase) {
                            Phase.MOVE -> startMove()
                            Phase.MAIN2 -> startMain2()
                            Phase.END_TURN -> endTurn()
                            else -> {
                                { playerData }
                            }
                        }

                        Single.just(phasebla(playerData))
//                        Single.just(playerData)
                                .flatMapCompletable { intoDb -> playerDataSource.insertAll(intoDb) }
                                .andThen(Single.just(NextPhaseInfo(playerData, playerData.phase)))
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
                            } else emitter.onError(MoveEveryStepException(playerData.playerColor))
                        }
                    }

    private fun canEndMain2(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.MAIN2)

    private fun canEndTurn(playerData: PlayerData): Single<PlayerData> =
            checkPhase(playerData.playerColor, Phase.END_TURN)


    private fun startMove(): (PlayerData) -> PlayerData = { player ->
        player.apply { steps.add(graphicController.getPlayerField(playerColor).gamePosition) }
                .also {
                    val position = graphicController.getPlayerPosition(player.playerColor)
                    fieldController.setConnectionColor(player.toConnectionInfo(position))
                }
    }

    private fun startMain2(): (PlayerData) -> PlayerData = {
        obtainField(it).also {
            val nextPhaseInfo: NextPhaseInfo = NextPhaseInfo(it, Phase.MAIN2)
            fieldController.setConnectionColor(nextPhaseInfo.toConnectionInfo(graphicController.getPlayerPosition(it.playerColor)))
        }
    }

    private fun endTurn(): (PlayerData) -> PlayerData = {
        it.also {
            graphicController.players
                    .apply {
                        val oldPlayer = this[0]
                        var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
                        forEach { player ->
                            player.getGameImage().zIndex = indexOld++
                        }
                        removeAt(0)
                        add(oldPlayer)

                        Logger.println("oldPlayer: ${oldPlayer.playerColor}")
                        //TODO items in db
                        oldPlayer.playerItems.removeUsedItems()
                    }.let {
                        it.first()
                    }.also { newPlayer ->
                        Logger.println("newPlayer: ${newPlayer.playerColor}")
                        playerColorDispender.publishUpdate(newPlayer.playerColor)
                    }
        }
    }

    private fun obtainField(playerData: PlayerData): PlayerData =
            graphicController
                    .getPlayerField(playerData.playerColor)
                    .apply {
                        val items =
                                mutableListOf<Item>().apply { addAll(disposedItems) }
                        items.forEach { it.use(playerData) }
                    }
                    .fieldType
                    .also {
                        when (it) {
                            FieldType.WIN -> obtainWinCommand(playerData)
                            FieldType.LOSE -> obtainLoseCommand(playerData)
                            FieldType.AMBUSH -> obtainAmbushCommand(playerData)
                            FieldType.GIFT -> obtainGiftCommand(playerData)
                            FieldType.MINE -> obtainMineCommand(playerData)
                            FieldType.TUNNEL -> obtainTunnelCommand(playerData)
                            FieldType.GOAL -> obtainGoalCommand(playerData)
                            FieldType.SHOP -> getDefault().post(ObtainShopCommand(playerData)) //TODO open shop onSuccess
                            else -> Logger.println("IMPL ME")
                        }
                        Logger.println("Field ${it.name}: $playerData")
                    }
                    .let { playerData }

    private fun obtainGoalCommand(playerData: PlayerData) {
        if (fieldController.currentGoal?.gamePosition?.isPosition(graphicController.getPlayerPosition(playerData.playerColor)) == true) {
            playerData.apply {
                credits += GOAL_CREDITS
                victories++
            }
            fieldController.setRandomGoal()
        }
    }

    private fun obtainTunnelCommand(playerData: PlayerData) {
        val tunnel = getRandomTunnel(playerData.playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        graphicController.getPlayer(playerData.playerColor).setFieldPosition(tunnel)
    }

    private fun getRandomTunnel(playerColor: PlayerColor): SpaceField {
        val playerPosition = graphicController.getPlayerPosition(playerColor)
        val tunnelList = fieldController.fieldsMap[FieldType.TUNNEL]!!
        var tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]

        while (tunnel.gamePosition.isPosition(playerPosition)) {
            tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]
        }
        return tunnel
    }

    private fun obtainMineCommand(playerData: PlayerData) {
        (graphicController.getPlayerField(playerData.playerColor) as MineField)
                .apply { owner = playerData.playerColor }
    }

    private fun obtainGiftCommand(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor).addRandomGift()
    }

    private fun obtainAmbushCommand(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor)
                .attachItem(ItemCollection.SLOW_MINE.create(playerData.playerColor) as DisposableItem)
    }

    private fun obtainLoseCommand(playerData: PlayerData) {
        playerData.substractRandomWin()
    }

    private fun obtainWinCommand(playerData: PlayerData) {
        playerData.addRandomWin()
    }

}