package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.GameObject

class GameScreen(game: MainGame, previousScreen: BaseScreen) : BaseScreen(game, previousScreen) {

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(game, this)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

    override fun getCameraTarget(): GameObject? {
        return game.gameController.playerController.currentPlayer
    }

    fun onZoomPlusClicked() {
        currentZoom--
        zoom()
    }

    fun onZoomMinusClicked() {
        currentZoom++
        zoom()
    }
}
