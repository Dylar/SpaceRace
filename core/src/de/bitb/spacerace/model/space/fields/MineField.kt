package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.enums.FieldType

class MineField(id: Int = -1, fieldType: FieldType = FieldType.MINE, img: Texture = fieldType.texture) : SpaceField(id, fieldType, img) {

    private var owner: Player? = null

    fun setOwner(player: Player) {
        owner = player
    }

    fun harvestOres(): Int {
        return if (owner == null) 0 else owner!!.addRandomWin()
    }

}