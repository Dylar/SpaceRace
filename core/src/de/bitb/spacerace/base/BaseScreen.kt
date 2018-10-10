package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

open class BaseScreen(val game: BaseGame) : Screen, InputProcessor {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    val stage: Stage = Stage(ScreenViewport())

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        val camera = stage.camera

        var posX = Gdx.input.x.toFloat()
        var posY = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
        camera.position.set(posX, posY, 0f)
        return true
    }

    override fun hide() {
    }


    override fun show() {
        Gdx.input.inputProcessor = stage
    }

//    public boolean touchDragged(int screenX, int screenY, int pointer)
//    {
//        camera.position.set(camera.position.x + screenX, camera.position.y + screenY);
//    }

    override fun render(delta: Float) {
        game.clearScreen()
        stage.act(delta)
        stage.draw()
//        game.batch.begin()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
    }
}