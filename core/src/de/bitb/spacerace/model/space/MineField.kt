package de.bitb.spacerace.model.space

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.player.Ship
import de.bitb.spacerace.model.enums.FieldType

class MineField(id: Int = -1, fieldType: FieldType = FieldType.MINE, img: Texture = fieldType.texture) : SpaceField(id, fieldType, img) {

    private var owner: Ship? = null

    fun setOwner(ship: Ship) {
        owner = ship
    }

    fun harvestOres(): Int {
        return if (owner == null) 0 else owner!!.addRandomWin()
    }

}