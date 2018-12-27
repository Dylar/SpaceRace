package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.GameController

class GameStage(screen: GameScreen) : BaseStage() {

    init {
        val controller = screen.game.gameController
        addEntitiesToMap(controller)
    }

    private fun addEntitiesToMap(gameController: GameController) {
        addActor(gameController.fieldController.connections)

        for (spaceGroup in gameController.fieldController.fieldGroups) {
            addActor(spaceGroup)
        }

        for (player in gameController.playerController.players) {
            addActor(player)
        }
    }

}