package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.InputHandler


class GameScreen(game: BaseGame, val inputHandler: InputHandler = InputHandler()) : BaseScreen(game) {

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(this, inputHandler)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(inputHandler.space, this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(inputHandler.space, this)
    }

    override fun renderGame(delta: Float) {
        val batch = gameStage.batch
        batch.begin()
        for (connection in inputHandler.space.fieldController.connections) {
            connection.draw(batch, 1f, gameStage.camera.combined)
        }
        batch.end()
        super.renderGame(delta)
    }

    override fun getCameraTarget(): BaseObject? {
        return inputHandler.space.playerController.currentPlayer
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
