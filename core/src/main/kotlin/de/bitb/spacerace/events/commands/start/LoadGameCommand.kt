package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import javax.inject.Inject

class LoadGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return SELECTED_PLAYER.size > 1
    }

    override fun execute() {
        graphicController.clearGraphics()
        val map = game.initMap(SELECTED_PLAYER, SELECTED_MAP.name)
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))

        val config = LoadGameConfig(map = map)

        loadGameUsecase.getResult(
                params = config,
                onSuccess = { info ->
                    graphicController.setGoal(NONE_POSITION to info.map.goal.gamePosition)
                    game.startGameDELETE_ME()
                })
    }

}