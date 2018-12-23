package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

abstract class BaseStage(viewport: Viewport = ScreenViewport()) : Stage(viewport) {

    companion object {
        val NONE = object : BaseStage(){}
    }

    var posX: Float = 0f
    var posY: Float = 0f

    open fun translateBy(distanceX: Float, distanceY: Float) {
        posX += distanceX
        posY += distanceY
    }

    fun translateTo(posX: Float, posY: Float) {
        this.posX = posX
        this.posY = posY
    }

    fun setColor(red: Float = 1f, green: Float = 1F, blue: Float = 1f, alpha: Float = 1f) {
        batch!!.color = Color(red, green, blue, alpha)
    }

    fun clearColor() {
        setColor()
    }

}