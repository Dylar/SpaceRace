package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import de.bitb.spacerace.CameraActions.*
import de.bitb.spacerace.GestureListenerAdapter


open class BaseScreen(val game: BaseGame) : Screen, GestureDetector.GestureListener by GestureListenerAdapter() {

    val gameStage: Stage = Stage(ScreenViewport())
    val guiStage: Stage = Stage(ScreenViewport())
    var cameraStatus = CAMERA_FREE

    private var currentZoom: Float = 5f
    private var cameraTarget: BaseObject? = null

//    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
//        val camera = gameStage.camera
//
//        var posX = Gdx.input.x.toFloat()
//        var posY = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
//        camera.position.set(posX, posY, 0f)
//        return true
//    }

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(guiStage, gameStage, GestureDetector(this))
    }

    override fun render(delta: Float) {
        game.clearScreen()
        gameStage.act(delta)
        gameStage.draw()

        guiStage.act(delta)
        guiStage.draw()

        if (!cameraStatus.isFree()) {
            val posX = cameraTarget!!.x + cameraTarget!!.width / 2
            val posY = cameraTarget!!.y + cameraTarget!!.height / 2
            gameStage.camera.position.set(posX, posY, 0f)
            gameStage.camera.update()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        gameStage.viewport.update(width, height, true)
        guiStage.viewport.update(width, height, true)
    }

    override fun hide() {
    }

    override fun dispose() {
        gameStage.dispose()
        guiStage.dispose()
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
            val gameCam = gameStage.camera
            gameCam.translate(-deltaX * currentZoom, deltaY * currentZoom, 0f)
            gameCam.update()
        }
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        Gdx.app.log("INFO", "Zoom performed")

        val gameCam = gameStage.camera as OrthographicCamera
        gameCam.zoom = initialDistance / distance * currentZoom
        gameCam.update()

        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        Gdx.app.log("INFO", "panStop")
        currentZoom = (gameStage.camera as OrthographicCamera).zoom
        return false
    }

    fun lockCamera(target: BaseObject) {
        cameraTarget = target
        cameraStatus = if (CAMERA_LOCKED == cameraStatus) CAMERA_FREE else CAMERA_LOCKED
    }

}