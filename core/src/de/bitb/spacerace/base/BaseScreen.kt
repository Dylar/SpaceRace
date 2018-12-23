package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import de.bitb.spacerace.CameraActions.CAMERA_FREE
import de.bitb.spacerace.CameraActions.CAMERA_LOCKED
import de.bitb.spacerace.GestureListenerAdapter
import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.core.MainGame


open class BaseScreen(val game: MainGame) : Screen, GestureDetector.GestureListener by GestureListenerAdapter() {

    var backgroundStage: BaseStage = BaseStage.NONE
    var gameStage: BaseStage = BaseStage.NONE
    var guiStage: BaseStage = BaseStage.NONE
    var cameraStatus = CAMERA_FREE

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
        Gdx.input.inputProcessor = InputMultiplexer(guiStage, gameStage, GestureDetector(this))

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
        game.clearScreen(blue = 200f)

        renderBackground(delta)
        renderGame(delta)
        renderGui(delta)

        renderCamera(delta)
    }

    open fun renderCamera(delta: Float) {
        if (!cameraStatus.isFree()) {
            val cameraTarget = getCameraTarget()
            if (cameraTarget != null) {
                val posX = cameraTarget.x + cameraTarget.width / 2
                val posY = cameraTarget.y + cameraTarget.height / 2
                gameStage.camera.position.set(posX, posY, 0f)
                gameStage.camera.update()

                backgroundStage.translateTo(posX / currentZoom, -posY / currentZoom)

            }
        }
    }

    open fun getCameraTarget(): BaseObject? {
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
    }

    override fun dispose() {
        gameStage.dispose()
        guiStage.dispose()
        backgroundStage.dispose()
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        if (cameraStatus.isFree()) {
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

        Logger.println("Zoom: $currentZoom")
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