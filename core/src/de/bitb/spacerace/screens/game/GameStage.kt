package de.bitb.spacerace.screens.game

import com.badlogic.gdx.math.GridPoint2
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace

class GameStage(val space: BaseSpace, screen: GameScreen) : BaseStage() {

    private var spaceGrid = GridPoint2

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