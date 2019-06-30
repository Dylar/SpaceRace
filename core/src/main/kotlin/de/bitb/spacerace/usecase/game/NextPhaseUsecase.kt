package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.obtain.ObtainShopCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource,
        private var playerColorDispender: PlayerColorDispender
) : UseCase<Boolean, PlayerData>(), DefaultFunction by DEFAULT {

    override fun buildUseCaseObservable(params: PlayerData) =
            params.let { playerData ->
                playerData.nextPhase()

                val doPhase: (PlayerData) -> PlayerData =
                        when (playerData.phase) {
                            Phase.MOVE -> startMove()
                            Phase.MAIN2 -> startMain2()
                            Phase.END_TURN -> endTurn()
                            else -> {
                                { playerData }
                            }
                        }
                Single.just(doPhase(playerData))
                        .flatMap { intoDb -> playerDataSource.insertAll(intoDb) }
                        .map {
                            Logger.println("DO NEXT PHASE")
                            it.isNotEmpty()
                        }
                        .doOnDispose { Logger.println("DISPOSE NEXT PHASE") }
                        .toObservable()
            }


    private fun startMove(): (PlayerData) -> PlayerData = {
        it.apply { steps.add(getPlayerField(fieldController, it.playerColor).gamePosition) }
    }

    private fun startMain2(): (PlayerData) -> PlayerData = {
        obstainField(it)
    }

    private fun endTurn(): (PlayerData) -> PlayerData = {
        it.also {
            playerController.players
                    .apply {
                        val oldPlayer = this[0]
                        var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
                        forEach {
                            it.getGameImage().zIndex = indexOld++
                        }
                        removeAt(0)
                        add(oldPlayer)

                        Logger.println("oldPlayer: ${oldPlayer.playerColor}")
                        //TODO items in db
                        //oldPlayer.playerData.playerItems.removeUsedItems()
                    }.let {
                        it.first()
                    }.also { newPlayer ->
                        Logger.println("newPlayer: ${newPlayer.playerColor}")
                        playerColorDispender.publishUpdate(newPlayer.playerColor)
                    }
        }
    }

    private fun obstainField(playerData: PlayerData): PlayerData {
//            field.disposedItems.forEach { it.use(game, playerData) } //TODO no game in here

        getPlayerField(fieldController, playerData.playerColor)
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
                        FieldType.SHOP -> EventBus.getDefault().post(ObtainShopCommand(playerData))
                        else -> Logger.println("IMPL ME")
                    }
                    Logger.println("Field ${it.name}: $playerData")
                }

        return playerData
    }

    private fun obtainGoalCommand(playerData: PlayerData) {
        if (fieldController.currentGoal == getPlayerField(fieldController, playerData.playerColor)) {
            playerData.apply {
                credits += GOAL_CREDITS
                victories++
            }
            fieldController.setRandomGoal()
        }
    }

    private fun obtainTunnelCommand(playerData: PlayerData) {
        val tunnel = fieldController.getRandomTunnel(playerData.playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        getPlayer(playerController, playerData.playerColor).setFieldPosition(tunnel)
    }

    private fun obtainMineCommand(playerData: PlayerData) {
        (getPlayerField(fieldController, playerData.playerColor) as MineField)
                .apply { owner = playerData.playerColor }
    }

    private fun obtainGiftCommand(playerData: PlayerData) {
        getPlayerItems(playerController, playerData.playerColor).addRandomGift()
    }

    private fun obtainAmbushCommand(playerData: PlayerData) {
        getPlayerItems(playerController, playerData.playerColor)
                .attachItem(ItemCollection.SLOW_MINE.create(playerData.playerColor) as DisposableItem)
    }

    private fun obtainLoseCommand(playerData: PlayerData) {
        playerData.substractRandomWin()
    }

    private fun obtainWinCommand(playerData: PlayerData) {
        playerData.addRandomWin()
    }

}