package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.CameraActions.*
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.ui.screens.game.BackgroundStage


abstract class BaseScreen(
        val game: MainGame,
        val previousScreen: BaseScreen?
) : Screen {
    companion object {
        var MAIN_DELTA = 0f
    }

    var cameraStatus = CAMERA_START

    var currentZoom: Float = 1f
        set(value) {
            field = when {
                value < MIN_ZOOM -> MIN_ZOOM
                value > MAX_ZOOM -> MAX_ZOOM
                else -> value
            }
        }

    override fun show() {
        val stages = getInputStages()
        Gdx.input.inputProcessor = InputMultiplexer(*stages.toTypedArray(), DebugInputProcessor)

        val gameCam = stages[1].camera as OrthographicCamera //TODO do this in another way
        gameCam.zoom = currentZoom
        zoom()
    }

    abstract fun getAllStages(): List<Stage> //guiStage, gameStage
    abstract fun getInputStages(): List<Stage> //guiStage, gameStage

    override fun render(delta: Float) {
        MAIN_DELTA += delta

        actScreen(delta)
        renderScreen()
        renderCamera()

        if (MAIN_DELTA > 1f) {
            MAIN_DELTA = 0f
        }
    }

    abstract fun actScreen(delta: Float)
    abstract fun renderScreen()
    abstract fun renderCamera()

    open fun renderCamera(backgroundStage: BackgroundStage, gameStage: Stage) {
        when (cameraStatus) {
            CAMERA_START,
            CAMERA_LOCKED -> {
                getCameraTarget()?.also {
                    if (cameraStatus == CAMERA_START) {
                        cameraStatus = CAMERA_FREE
                    }
                    val posX = it.getCenterX()
                    val posY = it.getCenterY()

                    gameStage.camera.position.set(posX, posY, 0f)
                    gameStage.camera.update()

                    backgroundStage.translateTo(posX / currentZoom, -posY / currentZoom)
                }
            }
            else -> {
            }
        }
    }

    open fun getCameraTarget(): GameImage? {
        return null
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
        backgroundStage.disposeDisposables()
        gameStage.disposeDisposables()
        guiStage.disposeDisposables()
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
        (gameStage.camera as OrthographicCamera).zoom = currentZoom
        return false
    }

    fun centerCamera(lock: Boolean = cameraStatus == CAMERA_FREE) {
        cameraStatus = if (lock) CAMERA_LOCKED else CAMERA_FREE
    }

}