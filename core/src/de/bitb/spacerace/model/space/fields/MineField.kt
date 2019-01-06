package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor

class MineField(fieldType: FieldType = FieldType.MINE) : SpaceField(fieldType) {

    var owner: PlayerColor = PlayerColor.NONE
        set(ow) {
            if (ow != PlayerColor.NONE) {
                blinkingColor = Color(ow.color)
            }
            field = ow
        }

    fun harvestOres(game: MainGame): Int {
        return if (owner == NONE) 0 else getPlayerData(game, owner).addRandomWin()
    }

}