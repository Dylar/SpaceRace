package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.model.space.control.BaseSpace

class GameStage(val space: BaseSpace, screen: GameScreen) : BaseStage() {

    init {
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