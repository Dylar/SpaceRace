package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.start.StartGameEvent

class GameScreen(game: MainGame) : BaseScreen(game) {

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(game, this)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

    override fun show() {
        super.show()
        game.gameController.inputHandler.handleCommand(StartGameEvent())
    }

    override fun renderGame(delta: Float) {
//        LineRenderer.startLine(Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH, gameStage.camera.combined)
//        val gameController = game.gameController
//        val playerData = gameController.playerController.currentPlayer.playerData
//        gameController.fieldController.connections.draw(playerData) //TODO do it with actor
//        LineRenderer.endLine()

        super.renderGame(delta)
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
