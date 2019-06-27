package de.bitb.spacerace.events.commands.gameover

import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.GameOverScreen
import javax.inject.Inject

class GameOverCommand(val winner: PlayerColor = PlayerColor.NONE) : BaseCommand() {

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        game.clear()
        inputHandler.removeListener()
        game.changeScreen(GameOverScreen(game))
    }
}