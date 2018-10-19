package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport

open class BaseStage(viewport: Viewport = ScreenViewport()) : Stage(viewport) {

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

    fun clearColor() {
        batch!!.color = Color(1f, 1f, 1f, 1f)
    }

}