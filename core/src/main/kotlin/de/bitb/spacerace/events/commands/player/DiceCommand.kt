package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class DiceCommand(
        playerData: PlayerData,
        val maxResult: Int = DICE_MAX
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        if (!playerData.phase.isMain1()) {
            return false
        }
        val items = getPlayerItems(playerController, playerData.playerColor)
        val diceCharges = 1 + items.multiDiceItem.sumBy { it.getAmount() }
        return playerData.diceResults.size < diceCharges
    }

    override fun execute() {
        val result = (Math.random() * maxResult).toInt() + 1
        playerData.diceResults.add(result)
        get bei minus minen mindestens ne 1
        updatePlayerUsecase.execute(
                params = listOf(playerData),
                onComplete = {
                    Logger.println("onComplete")
                },
                onError = {
                    Logger.println("onError")
                })
    }

}