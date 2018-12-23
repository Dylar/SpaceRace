package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.LineRenderer

class GameScreen(game: BaseGame, inputHandler: InputHandler) : BaseScreen(game, inputHandler) {

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(this, inputHandler)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(inputHandler, this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

    override fun renderGame(delta: Float) {
        LineRenderer.startLine(Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH, gameStage.camera.combined)
        for (connection in inputHandler.gameController.fieldController.connections) {
            connection.draw(inputHandler.gameController.playerController.currentPlayer.playerData)
        }
        LineRenderer.endLine()

        super.renderGame(delta)
    }

    override fun getCameraTarget(): BaseObject? {
        return inputHandler.gameController.playerController.currentPlayer
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
