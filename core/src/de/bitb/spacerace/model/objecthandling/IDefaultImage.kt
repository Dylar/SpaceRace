package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.base.BaseObject

interface IDefaultImage {

    fun setBlinkColor(blinkColor: Color?)
    fun getBlinkColor(delta: Float, currentColor: Color): Color?

    fun moveTo(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float = 0f, targetHeight: Float = 0f)
    fun getDistanceToTarget(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float
    fun getDurationToTarget(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float
}