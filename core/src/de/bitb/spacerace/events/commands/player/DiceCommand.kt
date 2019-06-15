package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class DiceCommand(playerData: PlayerData, val maxResult: Int = DICE_MAX) : BaseCommand(playerData) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        if (!playerData.phase.isMain1()) {
            return false
        }
        val items = getPlayerItems(game, playerData.playerColor)
        val diceCharges = 1 + items.multiDiceItem.sumBy { it.getAmount() }
        return playerData.diceResults.size < diceCharges
    }

    override fun execute(game: MainGame) {
        val result = (Math.random() * maxResult).toInt() + 1
        playerData.diceResults.add(result)
        Logger.println("DiceResult: $result")
        updatePlayerUsecase.execute(listOf(playerData))
    }

}