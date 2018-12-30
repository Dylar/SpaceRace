package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.player.PlayerData

class SpaceConnection(val spaceField1: SpaceField, val spaceField2: SpaceField) {

    fun getColor(playerData: PlayerData): Color {
        val isConnected = isConnected(playerData.fieldPosition)
        if (isConnected) {
            val canMove = playerData.canMove()
            if (canMove || isConnected(playerData.previousStep)) {
                return Color.GREEN
            }
        }

        return Color.RED
    }

    fun draw(playerData: PlayerData) {
        val start = Vector2(spaceField1.getAbsolutX(), spaceField1.getAbsolutY())
        val end = Vector2(spaceField2.getAbsolutX(), spaceField2.getAbsolutY())
        LineRenderer.drawDebugLine(start, end, getColor(playerData))
    }

    fun isConnected(spaceField: SpaceField): Boolean {
        return this.spaceField2 == spaceField || this.spaceField1 == spaceField
    }

    fun isConnection(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        return this.spaceField1 == spaceField1 && this.spaceField2 == spaceField2
                || this.spaceField1 == spaceField2 && this.spaceField2 == spaceField1
    }
}