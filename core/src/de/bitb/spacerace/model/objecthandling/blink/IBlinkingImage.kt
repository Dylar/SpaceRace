package de.bitb.spacerace.model.objecthandling.blink

import com.badlogic.gdx.graphics.Color

interface IBlinkingImage {

    fun setBlinkColor(blinkColor: Color?)
    fun getBlinkColor(delta: Float, currentColor: Color): Color?
}