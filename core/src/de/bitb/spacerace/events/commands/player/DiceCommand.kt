package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class DiceCommand(playerColor: PlayerData) : BaseCommand(playerColor) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return canDice(playerData)
    }

    fun canDice(playerData: PlayerData): Boolean {
        if (!playerData.phase.isMain1()) {
            return false
        }

        var diceCharges = 1
        for (diceModItem in playerData.playerItems.multiDiceItem) {
            diceCharges += diceModItem.getAmount()
        }
        return playerData.diceResults.size < diceCharges
    }

    override fun execute(game: MainGame) {
        playerData
                .apply { dice(this) }
                .also { updatePlayerUsecase.execute(listOf(it)) }
    }

    private fun dice(playerData: PlayerData, maxResult: Int = DICE_MAX) {
        val result = (Math.random() * maxResult).toInt() + 1
        playerData.diceResults.add(result)
        Logger.println("DiceResult: $result")
    }

}