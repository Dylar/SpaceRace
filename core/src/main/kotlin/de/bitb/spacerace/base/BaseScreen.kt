package de.bitb.spacerace.base

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import de.bitb.spacerace.CameraActions.*
import de.bitb.spacerace.GestureListenerAdapter
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.grafik.model.objecthandling.GameImage


open class BaseScreen(
        val game: MainGame,
        val previousScreen: BaseScreen?
) : Screen, GestureDetector.GestureListener by GestureListenerAdapter() {
    companion object {
        var MAIN_DELTA = 0f
    }

    var backgroundStage: BaseStage = BaseStage.NONE
    var gameStage: BaseStage = BaseStage.NONE
    var guiStage: BaseStage = BaseStage.NONE
    var cameraStatus = CAMERA_START

    var currentZoom: Float = 2f
        set(value) {
            if (value in MIN_ZOOM..MAX_ZOOM) {
                field = value
            }
        }

    override fun show() {

        guiStage = createGuiStage()
        gameStage = createGameStage()
        backgroundStage = createBackgroundStage()
        Gdx.input.inputProcessor = InputMultiplexer(guiStage, gameStage, GestureDetector(this), object : InputProcessor {
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

            override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
                return true
            }

            override fun keyDown(keycode: Int): Boolean {
                Logger.justPrint("KEY DOWN: ${Input.Keys.toString(keycode)}, KEY CODE: $keycode")
                return true
            }

            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                return true
            }

        })

        val gameCam = gameStage.camera as OrthographicCamera
        gameCam.zoom = currentZoom
        zoom()
    }

    open fun createBackgroundStage(): BaseStage {
        return BaseStage.NONE
    }

    open fun createGuiStage(): BaseStage {
        return BaseStage.NONE
    }

    open fun createGameStage(): BaseStage {
        return BaseStage.NONE
    }

    override fun render(delta: Float) {
        MAIN_DELTA += delta
        game.clearScreen(blue = 200f)

        renderBackground(delta)
        renderGame(delta)
        renderGui(delta)

        renderCamera(delta)

        if (MAIN_DELTA > 1f) {
            MAIN_DELTA = 0f
        }
    }

    open fun renderCamera(delta: Float) {
        when (cameraStatus) {
            CAMERA_START,
            CAMERA_LOCKED -> {
                getCameraTarget()?.also {
                    if (cameraStatus == CAMERA_START) {
                        cameraStatus = CAMERA_FREE
                    }
                    val posX = it.getCenterX()
                    val posY = it.getCenterY()
                    setCameraPosition(posX, posY)

                    backgroundStage.translateTo(posX / currentZoom, -posY / currentZoom)
                }
            }
            else -> {
            }
        }
    }

    fun setCameraPosition(posX: Float, posY: Float) {
        gameStage.camera.position.set(posX, posY, 0f)
        gameStage.camera.update()
    }

    open fun getCameraTarget(): GameImage? {
        return null
    }

    open fun renderBackground(delta: Float) {
        backgroundStage.act(delta)
        backgroundStage.draw()
    }

    open fun renderGame(delta: Float) {
        gameStage.act(delta)
        gameStage.draw()
    }

    open fun renderGui(delta: Float) {
        guiStage.act(delta)
        guiStage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        gameStage.viewport.update(width, height, true)
        guiStage.viewport.update(width, height, true)
        backgroundStage.viewport.update(width, height, true)
    }

    override fun hide() {
        backgroundStage.compositDisposable.clear()
        gameStage.compositDisposable.clear()
        guiStage.compositDisposable.clear()
    }

    override fun dispose() {
        gameStage.dispose()
        guiStage.dispose()
        backgroundStage.dispose()
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        if (cameraStatus == CAMERA_FREE) {
            var gameCam = gameStage.camera as OrthographicCamera
            gameCam.translate(-deltaX * gameCam.zoom, deltaY * gameCam.zoom, 0f)
            gameCam.update()

            gameCam = backgroundStage.camera as OrthographicCamera
            backgroundStage.translateBy(-deltaX * gameCam.zoom, -deltaY * gameCam.zoom)
        }
        return false
    }

    fun zoom(zoom: Float = 1f) {
        val gameCam = gameStage.camera as OrthographicCamera
        gameCam.zoom = zoom * currentZoom
        gameCam.update()
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        zoom(initialDistance / distance)
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        currentZoom = (gameStage.camera as OrthographicCamera).zoom
        return false
    }

    fun centerCamera(lock: Boolean = cameraStatus == CAMERA_FREE) {
        cameraStatus = if (lock) CAMERA_LOCKED else CAMERA_FREE
    }

}