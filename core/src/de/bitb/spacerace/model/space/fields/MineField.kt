package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.Player

class MineField(fieldType: FieldType = FieldType.MINE) : SpaceField(fieldType) {

    var owner: Player? = null

    fun harvestOres(): Int {
        return if (owner == null) 0 else owner!!.addRandomWin()
    }

}