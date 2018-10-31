package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2


object LineRenderer : com.badlogic.gdx.graphics.glutils.ShapeRenderer() {
    fun drawDebugLine(start: Vector2, end: Vector2, lineWidth: Int = 2, color: Color = Color.RED, projectionMatrix: Matrix4) {
        Gdx.gl.glLineWidth(lineWidth.toFloat())
        setProjectionMatrix(projectionMatrix)
        begin(ShapeType.Line)
        setColor(color)
        line(start, end)
        end()
        Gdx.gl.glLineWidth(1f)
    }
//
//    fun drawDebugLine(start: Vector2, end: Vector2, projectionMatrix: Matrix4) {
//        Gdx.gl.glLineWidth(2f)
//        setProjectionMatrix(projectionMatrix)
//        begin(ShapeType.Line)
//        color = Color.RED
//        line(start, end)
//        end()
//        Gdx.gl.glLineWidth(120f)
//    }
}