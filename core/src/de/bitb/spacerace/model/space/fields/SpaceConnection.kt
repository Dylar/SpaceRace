package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerData

class SpaceConnection(val spaceField1: SpaceField, val spaceField2: SpaceField) {

    fun getColor(playerData: PlayerData, positionData: PositionData): Color {
        val isConnected = isConnected(positionData)
        var color = Color.CLEAR
        color.a = 0.6f
        if (isConnected) {
            val canMove = playerData.canMove()
            if (canMove || playerData.phase.isMoving() && isConnected(playerData.previousStep)) {
                color = Color.GREEN
                color.a = 0.8f
            }
        }

        return color
    }

    fun draw(playerData: PlayerData, positionData: PositionData) {
        val start = Vector2(spaceField1.fieldImage.getCenterX(), spaceField1.fieldImage.getCenterY())
        val end = Vector2(spaceField2.fieldImage.getCenterX(), spaceField2.fieldImage.getCenterY())
        LineRenderer.drawDebugLine(start, end, getColor(playerData, positionData))
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