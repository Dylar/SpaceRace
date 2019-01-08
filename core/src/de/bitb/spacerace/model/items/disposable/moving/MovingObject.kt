package de.bitb.spacerace.model.items.disposable.moving

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.WHITE
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.utils.CalculationUtils

class MovingObject(img: Texture, val movingState: MovingState, imageColor: Color = WHITE, val speed: Double = Math.random()) : Image(img) {

    var fieldPosition: SpaceField? = null

    init {
        setOrigin(width / 2, height / 2)
        color = imageColor
    }

    var angle = 0f
    var point = Vector2()

    val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()

    override fun act(delta: Float) {
        super.act(delta)
        if (MovingState.ROTATE_POINT == movingState) {
            if (actions.isEmpty) angle += slice * speed.toFloat() * delta else angle = 0f
            point = CalculationUtils.calculateRotationPoint(Vector2(fieldPosition!!.getAbsolutX() - width / 2, fieldPosition!!.getAbsolutY() - height / 2), (width * 2).toDouble(), angle.toDouble())
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (MovingState.ROTATE_POINT == movingState && actions.isEmpty) {
            setPosition(point.x, point.y)
        }
        super.draw(batch, parentAlpha)
    }

    fun moveTo(spaceField: SpaceField) {
        fieldPosition = spaceField
        val moveTo = MoveToAction()
        val point = CalculationUtils.calculateRotationPoint(Vector2(spaceField.getAbsolutX() - width / 2, spaceField.getAbsolutY() - height / 2), (width * 2).toDouble())

        moveTo.setPosition(point.x, point.y)
        moveTo.duration = (5f * speed).toFloat()
        addAction(moveTo)
    }
}