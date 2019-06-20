package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.CommandDispender
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Single
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource,
        private var playerColorDispender: PlayerColorDispender,
        private val commandDispender: CommandDispender
) : UseCase<Boolean, PlayerData>(), DefaultFunction by DEFAULT {

    override fun buildUseCaseObservable(params: PlayerData) =
            params.let { playerData ->
                val doPhase: (PlayerData) -> Single<PlayerData> =
                        when (playerData.phase) {
                            Phase.MAIN1,
                            Phase.END_ROUND -> {
                                { Single.just(playerData) }
                            }
                            Phase.MOVE -> startMove()
                            Phase.MAIN2 -> startMain2()
                            Phase.END_TURN -> endTurn()
                        }
                doPhase(playerData)
                        .flatMap { intoDb -> playerDataSource.insertAll(intoDb) }
                        .map { it.isNotEmpty() }
                        .toObservable()
            }


    private fun startMove(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.apply { steps.add(getPlayerField(fieldController, it.playerColor).gamePosition) })
    }

    private fun startMain2(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also { playerData ->
            val field = getPlayerField(fieldController, playerData.playerColor)
//            field.disposedItems.forEach { it.use(game, playerData) } //TODO no game in here

            val command: BaseCommand = when (field.fieldType) {
                FieldType.WIN -> ObtainWinCommand(playerData)
                FieldType.LOSE -> ObtainLoseCommand(playerData)
                FieldType.AMBUSH -> ObtainAmbushCommand(playerData)
                FieldType.GIFT -> ObtainGiftCommand(playerData)
                FieldType.MINE -> ObtainMineCommand(playerData)
                FieldType.TUNNEL -> ObtainTunnelCommand(playerData)
                FieldType.SHOP -> ObtainShopCommand(playerData)
                FieldType.GOAL -> ObtainGoalCommand(playerData)
                else -> {
                    Logger.println("IMPL ME")
                    ObtainLoseCommand(playerData)
                }
            }

//            FieldType.PLANET -> TODO()
//            FieldType.RANDOM -> TODO()
//            FieldType.UNKNOWN -> TODO()
            commandDispender.publishUpdate(command)
        })
    }

    private fun endTurn(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also {
            playerController.players
                    .apply {
                        val oldPlayer = this[0]
                        var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
                        forEach {
                            it.getGameImage().zIndex = indexOld++
                        }
                        removeAt(0)
                        add(oldPlayer)

                        //TODO items in db
                        //oldPlayer.playerData.playerItems.removeUsedItems()
                    }.also {
                        playerColorDispender.publishUpdate(it.first().playerColor)
                    }
        })
    }

}