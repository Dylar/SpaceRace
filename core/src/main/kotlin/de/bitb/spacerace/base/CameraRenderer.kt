package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.input.GestureDetector.*
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.GestureListenerAdapter
import de.bitb.spacerace.base.CameraAction.TARGET_ACTION
import de.bitb.spacerace.base.CameraAction.TARGET_ACTION.*
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.grafik.model.objecthandling.GameImage

sealed class CameraAction {
    sealed class TARGET_ACTION(val targetEntity: GameImage?) : CameraAction() {
        class CAMERA_START(startEntity: GameImage?) : TARGET_ACTION(startEntity)
        class CAMERA_LOCKED(lockedEntity: GameImage?) : TARGET_ACTION(lockedEntity)
    }

    object CAMERA_FREE : CameraAction()
}

interface CameraRenderer : GestureListener {
    var currentZoom: Float
    var cameraAction: CameraAction

    fun initCamera(entityStage: Stage, backgroundStage: BaseStage, centerOnEntity: GameImage? = null)
    fun centerCamera(targetEntity: GameImage?)
    fun zoom()
    fun renderCamera()
    fun addZoom(value: Float)
}

class CameraStateRenderer : CameraRenderer, GestureListener by GestureListenerAdapter() {

    override lateinit var cameraAction: CameraAction

    private lateinit var entityStage: Stage
    private lateinit var backgroundStage: BaseStage

    override var currentZoom: Float = 1f
        set(value) {
            field = when {
                value < MIN_ZOOM -> MIN_ZOOM
                value > MAX_ZOOM -> MAX_ZOOM
                else -> value
            }
        }

    override fun initCamera(
            entityStage: Stage,
            backgroundStage: BaseStage,
            centerOnEntity: GameImage?
    ) {
        this.entityStage = entityStage
        this.backgroundStage = backgroundStage
        cameraAction = CAMERA_START(centerOnEntity)

        entityStage.getOrthographicCamera().zoom = currentZoom
        zoom(1f, 1f)
    }

    override fun renderCamera() {
        when (cameraAction) {
            is CAMERA_START,
            is CAMERA_LOCKED -> {
                val target = (cameraAction as TARGET_ACTION).targetEntity
                target?.also {
                    if (cameraAction is CAMERA_START) {
                        cameraAction = CameraAction.CAMERA_FREE
                    }
                    val posX = it.getCenterX()
                    val posY = it.getCenterY()

                    setCameraPosition(posX, posY)
                }
            }
            else -> {
            }
        }
    }

    private fun setCameraPosition(posX: Float, posY: Float) {
        entityStage.camera.position.set(posX, posY, 0f)
        entityStage.camera.update()

        backgroundStage.translateTo(posX / currentZoom, -posY / currentZoom)
    }

    override fun centerCamera(targetEntity: GameImage?) {
        cameraAction = targetEntity?.let { CAMERA_LOCKED(it) } ?: CameraAction.CAMERA_FREE
    }

    override fun zoom() {
        zoom(1f, 1f)
    }

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
        when (cameraAction) {
            is CameraAction.CAMERA_FREE -> {
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