package de.bitb.spacerace.model.objecthandling

import de.bitb.spacerace.model.items.disposable.moving.MovingState

abstract class GameObject(var positionData: PositionData)
    : DefaultFunction by object : DefaultFunction {} {

    open var id: Int = 0
    var movingState = MovingState.NONE

    abstract fun getGameImage(): GameImage

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