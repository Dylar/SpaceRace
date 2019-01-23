package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2


object LineRenderer : com.badlogic.gdx.graphics.glutils.ShapeRenderer() {
    fun startLine(lineWidth: Int = 2, projectionMatrix: Matrix4) {
        Gdx.gl.glLineWidth(lineWidth.toFloat())
        setProjectionMatrix(projectionMatrix)
        begin(ShapeType.Line)
    }

    fun drawDebugLine(start: Vector2, end: Vector2, color: Color = Color.RED) {
        setColor(color)
        line(start, end)
    }

    fun endLine() {
        end()
        Gdx.gl.glLineWidth(1f)
    }
}