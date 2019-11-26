package de.bitb.spacerace.grafik.model.objecthandling.blink

import com.badlogic.gdx.graphics.Color

interface IBlinkingImage {

    fun setBlinkColor(blinkColor: Color?)
    fun getBlinkColor(currentColor: Color, default: Color = currentColor): Color
}