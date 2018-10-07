package de.bitb.spacerace.base

import com.badlogic.gdx.Screen

open class BaseScreen(val game: BaseGame) : Screen {

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        game.clearScreen()
        game.batch.begin()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }
}