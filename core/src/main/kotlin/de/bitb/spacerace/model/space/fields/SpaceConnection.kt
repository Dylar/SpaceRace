package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.COLOR_CONNECTED
import de.bitb.spacerace.config.COLOR_DISCONNECTED
import de.bitb.spacerace.model.objecthandling.PositionData

class SpaceConnection(
        val spaceField1: SpaceField,
        val spaceField2: SpaceField
) {

    var currentColor: Color = COLOR_DISCONNECTED

    fun setColor(isConnected: Boolean) {
        currentColor = if (isConnected) COLOR_CONNECTED else COLOR_DISCONNECTED
    }

    fun isConnected(spaceField: PositionData): Boolean {
        return spaceField1.gamePosition.isPosition(spaceField) || spaceField2.gamePosition.isPosition(spaceField)
    }

    fun isConnection(spaceField1: PositionData, spaceField2: PositionData): Boolean {
        return this.spaceField1.gamePosition.isPosition(spaceField1) &&
                this.spaceField2.gamePosition.isPosition(spaceField2)
                || this.spaceField2.gamePosition.isPosition(spaceField1) &&
                this.spaceField1.gamePosition.isPosition(spaceField2)
    }

    fun getOpposite(fieldPosition: SpaceField): SpaceField {
        return if (fieldPosition.gamePosition.isPosition(spaceField1.gamePosition)) spaceField2 else spaceField1
    }

    operator fun component1(): SpaceField = spaceField1
    operator fun component2(): SpaceField = spaceField2
    operator fun component3(): Color = currentColor

}