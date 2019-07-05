package de.bitb.spacerace.events.commands.gameover

import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor

class GameOverCommand(val winner: PlayerColor = PlayerColor.NONE) : BaseCommand() {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {

        //TODO do this as "command"
//
//        game.clear()
//        inputHandler.removeListener()
//
//        game.changeScreen(GameOverScreen(game))
    }
}