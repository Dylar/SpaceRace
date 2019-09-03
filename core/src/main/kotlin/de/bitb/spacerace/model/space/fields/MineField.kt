package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor

//TODO do this in field data blubb
class MineField : SpaceField(FieldType.MINE) {

    var owner: PlayerColor = PlayerColor.NONE
        set(ow) {
            if (ow != PlayerColor.NONE) {
                setBlinkColor(Color(ow.color))
            }
            field = ow
        }
}