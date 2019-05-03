package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.LoadPlayerUsecase
import javax.inject.Inject

class StartGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadPlayerUsecase: LoadPlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.gamePlayer.size > 1
    }

    override fun execute(game: MainGame) {
        Logger.println("EXECUTE StartGameCommand")
        val gameController = game.gameController
        gameController.inputHandler.removeListener()
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))

        loadPlayerUsecase(
                gameController.gamePlayer,
                onNext = {
                    //TODO make load game
                    gameController.initGame(game, it)
                },
                onError = {
                    Logger.println("NEXT ERROR: loadPlayerUsecase")
                    it.printStackTrace()
                })
    }


}