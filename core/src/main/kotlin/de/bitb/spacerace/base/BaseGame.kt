package de.bitb.spacerace.base

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

abstract class BaseGame : Game() {

    override fun create() {
        Gdx.input.isCatchBackKey = true
        initGame()
        initScreen()
    }

    abstract fun initGame()
    abstract fun initScreen()

    fun changeScreen(screen: BaseScreen) {
        setScreen(screen)
    }

    fun clearScreen(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
        Gdx.gl.glClearColor(red, green, blue, alpha)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT and GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

}