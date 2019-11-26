package de.bitb.spacerace.grafik.model.objecthandling.blink

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.base.BaseScreen.Companion.MAIN_DELTA

class BlinkingImage : IBlinkingImage {

    private val BLINKING_SPEED = 0.6
    private var blinkDelta: Float = MAIN_DELTA
    var blinkingColor: Color? = null

    override fun setBlinkColor(blinkColor: Color?) {
        blinkingColor = blinkColor
    }

    override fun getBlinkColor(currentColor: Color, default: Color): Color {
        return when {
            blinkingColor != null -> {
                var blinkColor = currentColor
                if (heartBeat()) {
                    blinkColor = if (currentColor == blinkingColor) Color(1f, 1f, 1f, 1f) else blinkingColor!!
                }
                blinkColor
            }
            else -> default
        }
    }

    private fun heartBeat(): Boolean {
        if (BLINKING_SPEED < MAIN_DELTA) {
            blinkDelta = MAIN_DELTA
        }

        if (blinkDelta != 1f && BLINKING_SPEED > MAIN_DELTA) {
            blinkDelta = 1f
            return true
        }

        return false
    }
}