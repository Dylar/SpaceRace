package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import de.bitb.spacerace.CameraActions.*
import de.bitb.spacerace.GestureListenerAdapter
import de.bitb.spacerace.Logger


open class BaseScreen(val game: BaseGame) : Screen, GestureDetector.GestureListener by GestureListenerAdapter() {

    var backgroundStage: Stage = Stage(ScreenViewport())
    var gameStage: Stage = Stage(ScreenViewport())
    var guiStage: Stage = Stage(ScreenViewport())
    var cameraStatus = CAMERA_FREE

    var currentZoom: Float = (gameStage.camera as OrthographicCamera).zoom
        set(value) {
            if (value < 4 && value >= 1) {
                field = value
            }
        }

    override fun show() {
        guiStage = createGuiStage()
        gameStage = createGameStage()
        Gdx.input.inputProcessor = InputMultiplexer(guiStage, gameStage, GestureDetector(this))
    }

    open fun createGuiStage(): Stage {
        return Stage(ScreenViewport())
    }

    open fun createGameStage(): Stage {
        return Stage(ScreenViewport())
    }

    override fun render(delta: Float) {
        game.clearScreen()

        renderBackground(delta)
        renderGame(delta)
        renderGui(delta)

        if (!cameraStatus.isFree()) {
            val cameraTarget = getCameraTarget()
            if (cameraTarget != null) {
                val posX = cameraTarget.x + cameraTarget.width / 2
                val posY = cameraTarget.y + cameraTarget.height / 2
                gameStage.camera.position.set(posX, posY, 0f)
                gameStage.camera.update()
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

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean {
//        val deltaX = pointer2!!.x - pointer1!!.x
//        val deltaY = pointer2.y - pointer1.y
//
//        var angle = Math.atan2(deltaY.toDouble(), deltaX.toDouble()).toFloat() * MathUtils.radiansToDegrees
//
//        angle += 90f
//
//        if (angle < 0) {
//            angle = 360f - -angle
//        }
//        for (actor in gameStage.actors) {
//            actor.rotation = -angle
//        }

        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        Gdx.app.log("INFO", "PAN")

        if (cameraStatus.isFree()) {
            var gameCam = gameStage.camera
            gameCam.translate(-deltaX * currentZoom, deltaY * currentZoom, 0f)
            gameCam.update()
            gameCam = backgroundStage.camera
            gameCam.translate(-deltaX, deltaY, 0f)
            gameCam.update()
        }
        return false
    }

    fun zoom(zoom: Float = 1f) {
        val gameCam = gameStage.camera as OrthographicCamera
        Logger.println("Zoom: $currentZoom")
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

    fun lockCamera(lock: Boolean = cameraStatus == CAMERA_FREE) {
        cameraStatus = if (lock) CAMERA_LOCKED else CAMERA_FREE
    }

}