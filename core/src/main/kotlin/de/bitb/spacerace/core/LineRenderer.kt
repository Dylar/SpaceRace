package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2


object LineRenderer : com.badlogic.gdx.graphics.glutils.ShapeRenderer() {

    fun startLine(projectionMatrix: Matrix4) {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        setProjectionMatrix(projectionMatrix)
        begin(ShapeType.Filled)
    }

    fun drawDebugLine(start: Vector2, end: Vector2, lineWidth: Float = 100f, color: Color = Color.RED) {
        setColor(color)
        rectLine(start.x, start.y, end.x, end.y, lineWidth)
    }

    fun endLine() {
        end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
    }
}