package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.config.DEBUG_WIN_FIELD
import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.exceptions.DiceFirstException
import de.bitb.spacerace.exceptions.StepsLeftException
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import de.bitb.spacerace.usecase.game.getter.GetTargetableFieldUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val graphicController: GraphicController,
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val getTargetableFieldUsecase: GetTargetableFieldUsecase,
        private val playerController: PlayerController,
        private val playerDataSource: PlayerDataSource,
        private val saveDataSource: SaveDataSource,
        private val mapDataSource: MapDataSource
) : ResultUseCase<NextPhaseResult, PlayerColor> {

    override fun buildUseCaseSingle(params: PlayerColor): Single<NextPhaseResult> =
            checkCurrentPlayerUsecase.buildUseCaseSingle(params)
                    .flatMap { checkEndable(it) }
                    .flatMap { doPhase(it) }
                    .flatMap { result ->
                        playerDataSource.insertAllReturnAll(result.player).map { result }
                    }

    private fun checkEndable(playerData: PlayerData) =
            when (playerData.phase) {
                Phase.MAIN1 -> canEndMain1(playerData)
                Phase.MOVE -> canEndMove(playerData)
                Phase.MAIN2 -> canEndMain2(playerData)
                Phase.END_TURN -> canEndTurn(playerData)
                Phase.END_ROUND -> Single.just(playerData)
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


    private fun doPhase(playerData: PlayerData): Single<out NextPhaseResult> =
            playerData.apply {
                phase = Phase.next(phase)
            }.let { player ->
                when (player.phase) {
                    Phase.MOVE -> startMove(player)
                    Phase.MAIN2 -> startMain2(player)
                    Phase.END_TURN -> endTurn(player)
                    else -> Single.just(NextPhaseResult(player))
                }
            }

    private fun startMove(playerData: PlayerData): Single<NextPhaseResult> =
            getTargetableFieldUsecase.buildUseCaseSingle(playerData)
                    .map {
                        playerData.apply { steps.add(playerData.gamePosition) }
                        StartMoveResult(playerData).apply { targetableFields.addAll(it) }
                    }

    private fun startMain2(playerData: PlayerData): Single<out NextPhaseResult> =
            obtainField(playerData)

    private fun endTurn(playerData: PlayerData): Single<NextPhaseResult> =
            Single.fromCallable {
                NextPhaseResult(playerData.also {
                    playerController.changePlayer()
                    playerController.currentColor
                })
            }

    //TODO clean me
    private fun obtainField(playerData: PlayerData): Single<out ObtainFieldResult> =
            Single.fromCallable {
                triggerItems(playerData)
                playerData.positionField.target
            }.flatMap { fieldData ->
                Logger.println("Field ${fieldData.fieldType.name}: $playerData")
                val restult: Single<out ObtainFieldResult> = when (fieldData.fieldType) {
                    FieldType.WIN -> obtainWin(playerData)
                    FieldType.LOSE -> obtainLose(playerData)
                    FieldType.AMBUSH -> obtainAmbush(playerData)
                    FieldType.GIFT -> obtainGift(playerData)
                    FieldType.MINE -> obtainMine(playerData)
                    FieldType.TUNNEL -> obtainTunnel(playerData)
                    FieldType.GOAL -> obtainGoal(playerData)
                    FieldType.SHOP -> obtainShop(playerData)
                    FieldType.PLANET,
                    FieldType.RANDOM,
                    FieldType.UNKNOWN -> Single.just(ObtainFieldResult(playerData))
                }
                restult
            }

    private fun obtainShop(playerData: PlayerData): Single<out ObtainShopResult> =
            Single.fromCallable {
                ObtainShopResult(playerData)
            }

    private fun triggerItems(playerData: PlayerData) {
        //TODO old code change that
        mutableListOf<Item>()
                .apply {
                    val field = graphicController.getPlayerFieldGraphic(playerData.playerColor)
                    addAll(field.disposedItems)
                }
                .forEach { it.use(playerData) }
    }

    private fun obtainGoal(playerData: PlayerData): Single<out ObtainGoalResult> =
            saveDataSource.getLoadedGame()
                    .map { map -> map to map.fields.filter { field -> field.fieldType == FieldType.GOAL } }
                    .flatMap { (map, goals) ->
                        var goal = map.goal.target
                        val checkGoalPosition =
                                if (goal.gamePosition.isPosition(playerData.gamePosition)) {
                                    Logger.println("oldGoal: $goal")
                                    playerData.apply {
                                        credits += GOAL_CREDITS
                                        victories++
                                    }

                                    goal = if (DEBUG_WIN_FIELD) goals.first()
                                    else goals[(Math.random() * goals.size).toInt()]

                                    Logger.println("newGoal: $goal")

                                    map.goal.target = goal
                                    saveDataSource.insertSaveData(map)
                                } else Completable.complete()
                        checkGoalPosition.andThen(Single.just(ObtainGoalResult(playerData, goal)))
                    }

    private fun obtainTunnel(playerData: PlayerData): Single<out ObtainTunnelResult> =
            mapDataSource.getFieldsLazy(FieldType.TUNNEL).map { fields ->
                val playerPosition = playerData.gamePosition
                var tunnelPosition = fields.random()
                while (tunnelPosition.gamePosition.isPosition(playerPosition)) {
                    tunnelPosition = fields.random()
                }

                //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
//                val field = graphicController.fieldGraphics[tunnelPosition]
//                        ?: NONE_SPACE_FIELD
//                graphicController.getPlayerGraphic(playerData.playerColor).setFieldPosition(field)
                playerData.positionField.target = tunnelPosition
                ObtainTunnelResult(playerData)
            }

    private fun obtainMine(playerData: PlayerData): Single<ObtainFieldResult> =
            Single.fromCallable {
                val mine = playerData.positionField.target
                mine.owner.target?.mines?.remove(mine)
                ObtainMineResult(playerData.apply { mines.add(mine) })
            }

    private fun obtainGift(playerData: PlayerData): Single<ObtainFieldResult> =
            Single.fromCallable {
                val gift = ItemData(itemType = ItemType.getRandomItem())
                gift.owner.target = playerData
                playerData.storageItems.add(gift)
                ObtainFieldResult(playerData)
            }

    private fun obtainAmbush(playerData: PlayerData): Single<ObtainFieldResult> =
            Single.fromCallable {
                playerData.attachedItems.add(ItemData(itemType = ItemType.SLOW_MINE()))
                ObtainFieldResult(playerData)
            }

    private fun obtainLose(playerData: PlayerData): Single<ObtainFieldResult> =
            Single.fromCallable {
                ObtainFieldResult(playerData.apply { substractRandomWin() })
            }

    private fun obtainWin(playerData: PlayerData): Single<ObtainFieldResult> =
            Single.fromCallable {
                ObtainFieldResult(playerData.apply { addRandomWin() })
            }

}

open class NextPhaseResult(var player: PlayerData, val targetableFields: MutableList<FieldData> = mutableListOf())

open class StartMoveResult(player: PlayerData) : NextPhaseResult(player)

open class ObtainFieldResult(player: PlayerData) : NextPhaseResult(player)
open class ObtainShopResult(player: PlayerData) : ObtainFieldResult(player)
class ObtainMineResult(
        player: PlayerData
) : ObtainFieldResult(player)

class ObtainGoalResult(
        player: PlayerData,
        var newGoal: FieldData
) : ObtainFieldResult(player)

class ObtainTunnelResult(
        player: PlayerData
) : ObtainFieldResult(player)
