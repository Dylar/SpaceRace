package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor

class MineField : SpaceField(FieldType.MINE) {

    var owner: PlayerColor = PlayerColor.NONE
        set(ow) {
            if (ow != PlayerColor.NONE) {
                setBlinkColor(Color(ow.color))
            }
            field = ow
        }
}