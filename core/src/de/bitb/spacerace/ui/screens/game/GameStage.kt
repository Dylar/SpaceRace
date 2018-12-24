package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.model.space.control.GameController

class GameStage(screen: GameScreen) : BaseStage() {

    init {
        val controller = screen.game.gameController
        controller.createSpace()
        createSpace(controller)
    }

    private fun createSpace(gameController: GameController) {
//TODO change line drawing in actor
        addActor(gameController.fieldController.connections)
//        for (connection in gameController.fieldController.connections) {
//            addActor(connection)
//        }
        for (spaceGroup in gameController.fieldController.fieldGroups) {
            addActor(spaceGroup)
        }
        for (ship in gameController.playerController.players) {
            addActor(ship)
        }
    }

}