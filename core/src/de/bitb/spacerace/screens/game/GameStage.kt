package de.bitb.spacerace.screens.game

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace

class GameStage(val space: BaseSpace, screen: GameScreen) : BaseStage() {

    init {
        createSpace()
    }

    private fun createSpace() {
        for (spaceField in space.fields) {
            addActor(spaceField)
        }
        for (ship in space.ships) {
            addActor(ship)
        }
    }

}