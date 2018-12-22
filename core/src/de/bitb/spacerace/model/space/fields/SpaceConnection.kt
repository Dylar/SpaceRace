package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.space.control.BaseSpace

class SpaceConnection(val space: BaseSpace, val spaceField1: SpaceField, val spaceField2: SpaceField) : Actor() {

    override fun getColor(): Color {
        val playerData = space.playerController.currentPlayer.playerData
        return if (playerData.phase.isMoving() && isConnected(playerData.fieldPosition)) {
            if (space.playerController.stepsLeft() == 0 && !isConnected(playerData.previousStep)) {
                Color.RED
            } else {
                Color.GREEN
            }
        } else {
            Color.RED
        }
    }


    fun draw(batch: Batch?, parentAlpha: Float, matrix: Matrix4) {
        super.draw(batch, parentAlpha)
        val start = Vector2(spaceField1.getAbsolutX(), spaceField1.getAbsolutY())
        val end = Vector2(spaceField2.getAbsolutX(), spaceField2.getAbsolutY())
        LineRenderer.drawDebugLine(start, end, GAME_CONNECTIONS_WIDTH, color, matrix)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        draw(batch, parentAlpha, stage.camera.combined)
    }

    fun isConnected(spaceField: SpaceField): Boolean {
        return this.spaceField2 == spaceField || this.spaceField1 == spaceField
    }

    fun isConnection(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        return this.spaceField1 == spaceField1 && this.spaceField2 == spaceField2
                || this.spaceField1 == spaceField2 && this.spaceField2 == spaceField1
    }
}