package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.model.space.control.TestSpace


class GameScreen(game: BaseGame) : BaseScreen(game) {

    private var space: BaseSpace = TestSpace()

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(space, this)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(space, this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(space, this)
    }

    override fun renderGame(delta: Float) {
        val batch = gameStage.batch
        batch.begin()
        for (connection in space.fieldController.connections) {
            connection.draw(batch, 1f, gameStage.camera.combined)
        }
        batch.end()
        super.renderGame(delta)
    }

    override fun getCameraTarget(): BaseObject? {
        return space.playerController.currentPlayer
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
