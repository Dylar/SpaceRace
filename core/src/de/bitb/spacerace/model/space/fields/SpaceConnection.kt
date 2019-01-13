package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerData

class SpaceConnection(val spaceField1: SpaceField, val spaceField2: SpaceField) {

    fun getColor(playerData: PlayerData, positionData: PositionData): Color {
        val isConnected = isConnected(positionData)
        if (isConnected) {
            val canMove = playerData.canMove()
            if (canMove || playerData.phase.isMoving() && isConnected(playerData.previousStep)) {
                return Color.GREEN
            }
        }

        return Color.RED
    }

    fun draw(playerData: PlayerData, positionData: PositionData) {
//        val start = Vector2(spaceField1.positionData.posX + spaceField1.positionData.width / 2, spaceField1.positionData.posY + spaceField1.positionData.height / 2)
//        val end = Vector2(spaceField2.positionData.posX + spaceField2.positionData.width / 2, spaceField2.positionData.posY + spaceField2.positionData.height / 2)
        val start = Vector2(spaceField1.fieldImage.x + spaceField1.fieldImage.width / 2, spaceField1.fieldImage.y+ spaceField1.fieldImage.height / 2)
        val end = Vector2(spaceField2.fieldImage.x  + spaceField2.fieldImage.width / 2, spaceField2.fieldImage.y + spaceField2.fieldImage.height / 2)
        LineRenderer.drawDebugLine(start, end, getColor(playerData, positionData))
    }

    fun isConnected(spaceField: PositionData): Boolean {
        return this.spaceField1.positionData.isPosition(spaceField) || this.spaceField2.positionData.isPosition(spaceField)
    }

    fun isConnection(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        return this.spaceField1.positionData.isPosition(spaceField1.positionData) && this.spaceField2.positionData.isPosition(spaceField2.positionData)
                || this.spaceField2.positionData.isPosition(spaceField1.positionData) && this.spaceField1.positionData.isPosition(spaceField2.positionData)
    }

    fun getOpposite(fieldPosition: SpaceField): SpaceField {
        return if (fieldPosition.positionData.isPosition(spaceField1.positionData)) spaceField2 else spaceField1
    }
}