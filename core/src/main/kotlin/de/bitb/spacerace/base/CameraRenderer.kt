package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.CameraAction
import de.bitb.spacerace.grafik.model.objecthandling.GameImage

interface CameraRenderer {
    var cameraAction: CameraAction

    var entityStage: Stage
    var backgroundStage: BaseStage

    fun initCamera(entityStage: Stage, backgroundStage: BaseStage, startX: Float, endY: Float, centerOnEntity: GameImage?)

    fun centerCamera(targetEntity: GameImage)
    fun zoom(initialDistance: Float = 1f, distance: Float = 1f)

    fun pan(deltaX: Float, deltaY: Float): Boolean
    fun panStop(): Boolean
    fun renderCamera()
}

class CameraStateRenderer : CameraRenderer {

    override lateinit var cameraAction: CameraAction

    override lateinit var entityStage: Stage
    override lateinit var backgroundStage: BaseStage

    private var currentZoom: Float = 1f

    override fun initCamera(
            entityStage: Stage,
            backgroundStage: BaseStage,
            startX: Float,
            endY: Float,
            centerOnEntity: GameImage?
    ) {
        this.entityStage = entityStage
        this.backgroundStage = backgroundStage
        cameraAction = CameraAction.TARGET_ACTION.CAMERA_START(centerOnEntity)

        val gameCam = entityStage.getOrthographicCamera()
        gameCam.zoom = currentZoom
        zoom()
    }

    override fun renderCamera() {
        when (cameraAction) {
            is CameraAction.TARGET_ACTION.CAMERA_START,
            is CameraAction.TARGET_ACTION.CAMERA_LOCKED -> {
                val target = (cameraAction as CameraAction.TARGET_ACTION).targetEntity
                target?.also {
                    if (cameraAction is CameraAction.TARGET_ACTION.CAMERA_START) {
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

    override fun centerCamera(targetEntity: GameImage) {
        cameraAction = CameraAction.TARGET_ACTION.CAMERA_LOCKED(targetEntity)
    }

    override fun zoom(
            initialDistance: Float,
            distance: Float
    ) {
        val gameCam = entityStage.getOrthographicCamera()
        gameCam.zoom = initialDistance / distance * currentZoom
        gameCam.update()
    }

//    override fun zoom(initialDistance: Float, distance: Float): Boolean {
//        zoom(initialDistance / distance)
//        return true
//    }

    override fun pan(
            deltaX: Float,
            deltaY: Float
    ): Boolean {
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

    override fun panStop(): Boolean {
        currentZoom = (entityStage.getOrthographicCamera()).zoom
        entityStage.getOrthographicCamera().zoom = currentZoom
        return false
    }

    private fun Stage.getOrthographicCamera(): OrthographicCamera = camera as OrthographicCamera
}