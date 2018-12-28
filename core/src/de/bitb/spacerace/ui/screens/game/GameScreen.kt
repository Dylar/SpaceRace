package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.MainGame

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

    override fun getCameraTarget(): BaseObject? {
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
