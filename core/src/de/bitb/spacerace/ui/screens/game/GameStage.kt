package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.model.space.control.GameController

class GameStage(val space: GameController, screen: GameScreen) : BaseStage() {

    init {
        space.createSpace()
        createSpace()
    }

    private fun createSpace() {
//        for (connection in space.connections) {
//            addActor(connection)
//        }
        for (spaceGroup in space.fieldController.fieldGroups) {
            addActor(spaceGroup)
        }
        for (ship in space.playerController.players) {
            addActor(ship)
        }
    }

}