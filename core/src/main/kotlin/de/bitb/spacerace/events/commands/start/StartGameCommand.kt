package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import javax.inject.Inject

class StartGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var playerController: PlayerController

    @Inject
    protected lateinit var fieldController: FieldController

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return SELECTED_PLAYER.size > 1
    }

    override fun execute() {

        Logger.println("EXECUTE StartGameCommand")
        inputHandler.removeListener()
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))
        loadGameUsecase.getResult(
                params = SELECTED_PLAYER,
                onSuccess = { players ->
                    //TODO make load game
                    Logger.println("NEXT: StartGameCommand")
                    game.startGameDELETE_ME()
                })
    }

}