package de.bitb.spacerace.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.model.space.BaseSpace

class GameStage(val space: BaseSpace, screen: GameScreen) : BaseStage() {

    init {
        createSpace()
    }

    private fun createSpace() {
        for (connection in space.connections) {
            addActor(connection)
        }
        for (spaceGroup in space.fieldGroups) {
            addActor(spaceGroup)
        }
//        for (ship in space.ships) {
//            addActor(ship)
//        }
    }

}