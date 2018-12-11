package de.bitb.spacerace.model.space

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH
import de.bitb.spacerace.core.LineRenderer

class SpaceConnection(val space: BaseSpace, val spaceField1: SpaceField, val spaceField2: SpaceField) : Actor() {

    override fun getColor(): Color {
        val shipField = space.currentPlayer.fieldPosition
        return if (space.phase.isMoving() && isConnected(shipField)) {
            if (space.stepsLeft() == 0 && !isConnected(space.previousStep)) {
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