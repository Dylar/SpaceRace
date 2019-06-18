package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.CommandDispender
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val commandDispender: CommandDispender,
        private val playerDataSource: PlayerDataSource
) : UseCase<Boolean, Pair<PlayerData, (PlayerData) -> Single<PlayerData>>>(), DefaultFunction by DEFAULT {

    override fun buildUseCaseObservable(params: Pair<PlayerData, (PlayerData) -> Single<PlayerData>>) =
            params.let { (playerData, doPhase) ->
                doPhase(playerData)
                        .flatMap { intoDb -> playerDataSource.insertAll(intoDb) }
                        .map { true }
                        .toObservable()
            }


    private fun startMove(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.apply { steps.add(getPlayerField(fieldController,it.playerColor).gamePosition) })
    }

    private fun startMain2(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also { playerData ->
            val field = getPlayerField(fieldController, playerData.playerColor)
            field.disposedItems.forEach { it.use(game, playerData) }

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
            EventBus.getDefault().post(command)
        })
    }

    private fun endTurn(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also {
            playerController.nextPlayer()
        })
    }

}