package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor

class DiceCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return getPlayerData(game, playerColor).canDice()
    }

    override fun execute(game: MainGame) {
        getPlayerData(game, playerColor).dice()
    }

}