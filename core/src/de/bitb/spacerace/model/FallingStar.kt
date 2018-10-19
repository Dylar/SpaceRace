package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
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
        color = Color.RED
        setOrigin(Align.center)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (isIdling()) {
            calculateValues()
            setPosition(startX, startY)
            Logger.println("ROTATION: $rotation")
            moveTo(endX, endY, 0f, 0f)
        }
    }

    private fun calculateValues(){
        val degrees = Math.atan2(
                (endY - startY).toDouble(),
                (endX - startX).toDouble()
        ) * 180.0 / Math.PI
        rotation = 125f + degrees.toFloat()
        movingSpeed = (Math.random() * 15f + 5).toFloat()
        startY = (Math.random() * Gdx.graphics.height).toFloat()
        endY = (Math.random() * Gdx.graphics.height).toFloat()
    }

}