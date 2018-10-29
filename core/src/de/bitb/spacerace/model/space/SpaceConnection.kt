package de.bitb.spacerace.model.space

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.LineRenderer

class SpaceConnection(val space: BaseSpace, val spaceField1: SpaceField, val spaceField2: SpaceField) : Actor() {

    override fun getColor(): Color {
        val shipField = space.currentShip.fieldPosition
        return if(shipField == spaceField1 || shipField == spaceField2) Color.GREEN else Color.RED
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        val start = Vector2(spaceField1.getAbsolutX(), spaceField1.getAbsolutY())
        val end = Vector2(spaceField2.getAbsolutX(), spaceField2.getAbsolutY())
        LineRenderer.DrawDebugLine(start, end, BaseGuiStage.lineWidth, color, stage.camera.combined)
    }

    fun isConnection(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        return this.spaceField1 == spaceField1 && this.spaceField2 == spaceField2
                || this.spaceField1 == spaceField2 && this.spaceField2 == spaceField1
    }
}