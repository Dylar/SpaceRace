package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.GestureListenerAdapter
import de.bitb.spacerace.base.CameraState.FREE
import de.bitb.spacerace.base.CameraState.LOCKED
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.ui.screens.BackgroundStage

sealed class CameraState {
    class LOCKED(val targetEntity: GameImage?) : CameraState()

    object FREE : CameraState()
}

interface CameraRenderer : GestureListener {
    var currentZoom: Float
    var cameraState: CameraState

    fun initCamera(baseScreen: BaseScreen, entityStage: Stage, backgroundStage: BackgroundStage, centerOnEntity: GameImage? = null)
    fun centerCamera(targetEntity: GameImage?)
    fun zoom(): Boolean
    fun renderCamera()
    fun addZoom(value: Float)
}

class CameraStateRenderer : CameraRenderer, GestureListener by GestureListenerAdapter() {

    override var cameraState: CameraState = FREE

    private lateinit var entityStage: Stage
    private lateinit var backgroundStage: BackgroundStage

    override var currentZoom: Float = 1f
        set(value) {
            field = when {
                value < MIN_ZOOM -> MIN_ZOOM
                value > MAX_ZOOM -> MAX_ZOOM
                else -> value
            }
        }

    override fun initCamera(
            baseScreen: BaseScreen,
            entityStage: Stage,
            backgroundStage: BackgroundStage,
            centerOnEntity: GameImage?
    ) {
        baseScreen.cameraInput = baseScreen as? GestureListener
        this.entityStage = entityStage
        this.backgroundStage = backgroundStage

        entityStage.getOrthographicCamera().zoom = currentZoom
        zoom(1f, 1f)
        centerOnEntity?.also { setCameraPosition(it.getCenterX(), it.getCenterY()) }
    }

    override fun renderCamera() {
        when (val state = cameraState) {
            is LOCKED -> state.targetEntity?.also { setCameraPosition(it.getCenterX(), it.getCenterY()) }
            is FREE -> {
            }
        }
    }

    private fun setCameraPosition(posX: Float, posY: Float) {
        entityStage.camera.position.set(posX, posY, 0f)
        entityStage.camera.update()

        backgroundStage.translateTo(posX / currentZoom, -posY / currentZoom)
    }

    override fun centerCamera(targetEntity: GameImage?) {
        cameraState = targetEntity?.let { LOCKED(it) } ?: FREE
    }

    override fun zoom() = zoom(1f, 1f)

    override fun zoom(
            initialDistance: Float,
            distance: Float
    ): Boolean {
        val gameCam = entityStage.getOrthographicCamera()
        gameCam.zoom = initialDistance / distance * currentZoom
        gameCam.update()
        return true
    }

    override fun addZoom(value: Float) {
        currentZoom += value
        zoom()
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        when (cameraState) {
            is FREE -> {
                var gameCam = entityStage.getOrthographicCamera()
                gameCam.translate(-deltaX * gameCam.zoom, deltaY * gameCam.zoom, 0f)
                gameCam.update()

                gameCam = backgroundStage.getOrthographicCamera()
                backgroundStage.translateBy(-deltaX * gameCam.zoom, -deltaY * gameCam.zoom)
            }
        }
        return false
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        currentZoom = (entityStage.getOrthographicCamera()).zoom
        entityStage.getOrthographicCamera().zoom = currentZoom
        return false
    }

    private fun Stage.getOrthographicCamera(): OrthographicCamera = camera as OrthographicCamera
}