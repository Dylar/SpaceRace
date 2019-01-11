package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor

class MineField(positionData: PositionData) : SpaceField(FieldType.MINE, positionData) {

    var owner: PlayerColor = PlayerColor.NONE
        set(ow) {
            if (ow != PlayerColor.NONE) {
                setBlinkColor(Color(ow.color))
            }
            field = ow
        }

    fun harvestOres(game: MainGame): Int {
        return if (owner == NONE) 0 else getPlayerData(game, owner).addRandomWin()
    }

}