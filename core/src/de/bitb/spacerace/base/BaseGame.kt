package de.bitb.spacerace.base

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class BaseGame : Game() {
    lateinit var batch: SpriteBatch

    override fun create() {
        System.out.println("create")
        batch = SpriteBatch()
        initScreen()
    }

    abstract fun initScreen()

    fun clearScreen(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
        Gdx.gl.glClearColor(red, green, blue, alpha)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batch.dispose()
    }

}