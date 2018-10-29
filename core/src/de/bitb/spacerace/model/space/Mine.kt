package de.bitb.spacerace.model.space

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.Ship
import de.bitb.spacerace.model.enums.FieldType

class Mine(id: Int = -1, fieldType: FieldType = FieldType.MINE, img: Texture = fieldType.texture) : SpaceField(id, fieldType, img) {

    private var owner: Ship? = null

    fun setOwner(ship: Ship) {
        owner = ship
    }

    fun collectProfit() {
        owner?.addRandomWin()
    }

}