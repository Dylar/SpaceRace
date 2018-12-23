package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.InputHandler

class GameStage(val space: InputHandler, screen: GameScreen) : BaseStage() {

    init {
        space.gameController.createSpace(space)
        createSpace()
    }

    private fun createSpace() {

//        addActor(space.gameController.fieldController.connections)
//        for (connection in gameController.fieldController.connections) {
//            addActor(connection)
//        }
        for (spaceGroup in space.gameController.fieldController.fieldGroups) {
            addActor(spaceGroup)
        }
        for (ship in space.gameController.playerController.players) {
            addActor(ship)
        }
    }

}