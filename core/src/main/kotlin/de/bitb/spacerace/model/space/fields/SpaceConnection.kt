package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH
import de.bitb.spacerace.grafik.LineRenderer
import de.bitb.spacerace.model.objecthandling.PositionData

class SpaceConnection(val spaceField1: SpaceField, val spaceField2: SpaceField) {

    var reverse = false
    var index = 0
    fun draw(color: Color) {
//        index += if (reverse) {
//            -1
//        } else {//TODO haha
//            +1
//        }
        reverse = if (index == 0) false else if (index > 50) true else reverse

        val start = Vector2(spaceField1.fieldImage.getCenterX(), spaceField1.fieldImage.getCenterY())
        val end = Vector2(spaceField2.fieldImage.getCenterX(), spaceField2.fieldImage.getCenterY())
        LineRenderer.drawDebugLine(start, end, GAME_CONNECTIONS_WIDTH + index, color)
    }

    fun isConnected(spaceField: PositionData): Boolean {
        return this.spaceField1.gamePosition.isPosition(spaceField) || this.spaceField2.gamePosition.isPosition(spaceField)
    }

    fun isConnection(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        return this.spaceField1.gamePosition.isPosition(spaceField1.gamePosition) && this.spaceField2.gamePosition.isPosition(spaceField2.gamePosition)
                || this.spaceField2.gamePosition.isPosition(spaceField1.gamePosition) && this.spaceField1.gamePosition.isPosition(spaceField2.gamePosition)
    }

    fun getOpposite(fieldPosition: SpaceField): SpaceField {
        return if (fieldPosition.gamePosition.isPosition(spaceField1.gamePosition)) spaceField2 else spaceField1
    }
}