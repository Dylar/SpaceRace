package de.bitb.spacerace.utils

import com.badlogic.gdx.math.Vector2

object CalculationUtils {

    fun calculateRotationPoint(rotationPoint: Vector2, radius: Double, angle: Double = 0.0): Vector2 {
        val point = Vector2()
        point.x = ((rotationPoint.x + radius * Math.cos(angle)).toFloat())
        point.y = ((rotationPoint.y + radius * Math.sin(angle)).toFloat())
        return point
    }
}