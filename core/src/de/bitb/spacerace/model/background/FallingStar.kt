package de.bitb.spacerace.model.background

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.core.TextureCollection

class FallingStar(var startX: Float = 0f,
                  var startY: Float = 0f,
                  var endX: Float = Gdx.graphics.width.toFloat(),
                  var endY: Float = Gdx.graphics.height.toFloat())
    : BaseObject(TextureCollection.fallingStar) {

    init {
        randomColor()
        scaleBy(-0.5f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (isIdling()) {
            randomColor()
            calculateValues()
            setPosition(startX, startY)
//            Logger.println("ROTATION: $rotation")
            moveTo(endX, endY)
        }
    }

    private fun randomColor() {
        val random: Int = (Math.random() * 3).toInt()
        color = when (random) {
            0 -> Color.RED
            1 -> Color.BLUE
            2 -> Color.GREEN
            else -> Color.YELLOW
        }
    }

    private fun calculateValues() {
        movingSpeed = (Math.random() * 35f + 25).toFloat()
        startY = (Math.random() * Gdx.graphics.height).toFloat()
        endY = (Math.random() * Gdx.graphics.height).toFloat()

        val degrees = Math.atan2(
                (endY - startY).toDouble(),
                (endX - startX).toDouble()
        ) * 180.0 / Math.PI
        rotation = 120f + degrees.toFloat()
    }

}