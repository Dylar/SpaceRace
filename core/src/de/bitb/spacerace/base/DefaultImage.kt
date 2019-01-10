package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color

class DefaultImage : IDefaultImage {

    var blinkTime: Float = 0f

    var blinkingColor: Color? = null

    override fun setBlinkColor(blinkColor: Color?) {
        blinkingColor = blinkColor
    }

    override fun getBlinkingColor(delta: Float, currentColor: Color): Color? {
        return when {
            blinkingColor != null -> {
                blinkTime += delta
                var blinkColor = currentColor
                if (blinkTime > 0.6) {
                    blinkTime = 0f
                    blinkColor = if (currentColor == blinkingColor) Color.SKY else blinkingColor!!
                }
                blinkColor
            }
            else -> null
        }
    }


}