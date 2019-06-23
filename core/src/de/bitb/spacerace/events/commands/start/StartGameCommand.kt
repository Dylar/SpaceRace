package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.init.LoadPlayerUsecase
import io.objectbox.Box
import javax.inject.Inject

class StartGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadPlayerUsecase: LoadPlayerUsecase

    @Inject
    protected lateinit var box: Box<PlayerData>

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.gamePlayer.size > 1
    }

    override fun execute(game: MainGame) {
        Logger.println("EXECUTE StartGameCommand")
        val gameController = game.gameController
        inputHandler.removeListener()
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))
        loadPlayerUsecase.getResult(
                gameController.gamePlayer,
                onSuccess = {
                    //TODO make load game
                    Logger.println("NEXT: StartGameCommand")
                    gameController.initGame(game, it)
                })
    }


}