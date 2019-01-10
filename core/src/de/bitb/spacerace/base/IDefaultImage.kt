package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color

interface IDefaultImage {

    fun setBlinkColor(blinkColor: Color?)
    fun getBlinkingColor(delta: Float, currentColor: Color): Color?
}