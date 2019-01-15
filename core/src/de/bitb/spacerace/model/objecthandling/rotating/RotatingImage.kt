package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameImage.Companion.NONE
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerImage
import de.bitb.spacerace.model.player.PlayerItems
import de.bitb.spacerace.utils.CalculationUtils

class RotatingImage(var speed: Double = Math.random()) : IRotatingImage {

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
    private var angle = 0.0
    private var radius: Double = 0.0

//    var centerPoint: PositionData = PositionData()

    override fun getRotationAngle(): Double {
        return angle
    }

    override fun setRotationRadius(radius: Double) {
        this.radius = radius
    }

    override fun getRotationAction(gameImage: PlayerImage, targetPosition: Vector2): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = NONE
//            centerPoint.posX = targetPosition.x
//            centerPoint.posY = targetPosition.y
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    override fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    override fun getNONEAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.NONE
        })
    }

    fun getRotationPoint(posX: Float, posY: Float, angle: Double): Vector2 {
        return CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                radius,
                angle
        )
    }

    override fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData {
        val point = getRotationPoint(gameImage, targetPosition, angle)
        return PositionData(posX = point.x - targetPosition.width / 2, posY = point.y - targetPosition.height / 2)
    }

    override fun getRotationPoint(gameImage: GameImage, followImage: GameImage, angle: Double): Vector2 {
        val posX = followImage.x + followImage.width / 2 - gameImage.width / 2
        val posY = followImage.y + followImage.height / 2 - gameImage.height / 2
        return getRotationPoint(posX, posY, angle)
    }

    override fun actRotation(gameImage: GameImage, delta: Float) {

        when (gameImage.movingState) {
            MovingState.ROTATE_POINT -> {
                if (gameImage.followImage == NONE) {
//                    setRotationPosition(gameImage, getRotationPoint(gameImage, gameImage.followImage, angle))
//                    setRotationPosition(gameImage, Vector2(centerPoint.posX, centerPoint.posX))
                } else {
                    angle += slice * speed * delta
                    setRotationPosition(gameImage, getRotationPoint(gameImage, gameImage.followImage, angle))
                }
            }
            MovingState.MOVING -> {
            }
            MovingState.NONE -> {

//                var currentImage = gameImage
//                while(currentImage.followImage != NONE){
//                    currentImage = currentImage.followImage
//                    val point1 = currentImage.getRotationPoint(currentImage, gameImage.followImage)

                if (gameImage.followImage != NONE) {
                    gameImage.x = gameImage.followImage.getCenterX()
                    gameImage.y = gameImage.followImage.getCenterY()
                }

//                }
//                val isChain =
//                if (isChain) {
//
//                        val point2 =  getRotationPoint(gameImage, gameImage, angle)
//                        setRotationPosition(gameImage, point)
//                }
            }
        }
    }

    override fun setRotationPosition(gameImage: GameImage, point: Vector2) {
        gameImage.setPosition(point.x, point.y)
    }
}