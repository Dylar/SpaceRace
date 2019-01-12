package de.bitb.spacerace.model.objecthandling

import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage

abstract class GameObject(var positionData: PositionData) :
        DefaultFunction by object : DefaultFunction {} {

    open var id: Int = 0
    var movingState = MovingState.NONE

    abstract fun getGameImage(): GameImage

//            = object : Image(texture) {
//        override fun draw(batch: Batch?, parentAlpha: Float) {
//            super.draw(batch, parentAlpha)
//            if (DEBUG_FIELDS) {
//                val label = TextButton("IAMGE: $id", TextureCollection.skin, "default")
//                label.label.width = positionData.width
//                label.setPosition(positionData.posX, positionData.posY)
//                label.color = Color.ROYAL
//                label.style.fontColor = Color.RED
//                label.draw(batch, parentAlpha)
//            }
//        }
//    }

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        val image = getGameImage()
        image.setBounds(x, y, width, height)
        positionData.posX = image.x
        positionData.posY = image.y
        positionData.width = image.width
        positionData.height = image.height
    }

    fun setPosition(x: Float, y: Float) {
        val image = getGameImage()
        image.setPosition(x, y)
        positionData.posX = image.x
        positionData.posY = image.y
    }

    fun setPosition(positionData: PositionData) {
        setPosition(positionData.posX, positionData.posY)
    }

    init {
//        setBounds(x, y, width, height)
//        setOrigin(positionData.width / 2, positionData.height / 2)
    }

//    var angle = 0f
//    var point = Vector2()
//
//    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()

//    fun act(delta: Float) {
//        image.act(delta)
//        if (MovingState.ROTATE_POINT == movingState) {
//            if (isIdling()) angle += slice * speed.toFloat() * delta else angle = 0f
//            point = CalculationUtils.calculateRotationPoint(Vector2(positionData.posX - positionData.width / 2, positionData.posY - positionData.height / 2), (positionData.width * 2).toDouble(), angle.toDouble())
//        }
//    }

//    fun draw(batch: Batch?, parentAlpha: Float) {
//        if (MovingState.ROTATE_POINT == movingState && isIdling()) {
//            setPosition(point.x, point.y)
//        }
//        super.draw(batch, parentAlpha)
//    }


//    fun moveTo(target: PositionData) {
//        moveTo(target.posX, target.posY, target.width, target.height)
//    }
//
//    fun moveTo(targetX: Float, targetY: Float, targetWidth: Float = 0f, targetHeight: Float = 0f) {
//    }

//    fun moveTo(positionData: PositionData) {
//        var point = when (movingState) {
//            MovingState.NONE -> Vector2(positionData.posX - width / 2, positionData.posY - height / 2)
//            MovingState.ROTATE_POINT -> CalculationUtils.calculateRotationPoint(Vector2(positionData.posX - width / 2, positionData.posY - height / 2), (width * 2).toDouble())
//            MovingState.MOVING -> TODO()
//        }
//
//        this.positionData = positionData
//        val moveTo = MoveToAction()
//
//        moveTo.setPosition(point.x, point.y)
//        moveTo.duration = (5f * speed).toFloat()
//        moveTo.duration = getDurationToTarget(positionData, targetX, targetY, targetWidth, targetHeight)
//        addAction(moveTo)
//    }
}