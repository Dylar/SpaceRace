package de.bitb.spacerace.model.objecthandling

abstract class GameObject(var positionData: PositionData = PositionData())
    : DefaultFunction by object : DefaultFunction {} {

    open var id: Int = 0

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
//        setOrigin(rotationPoint.width / 2, rotationPoint.height / 2)
    }

//    var angle = 0f
//    var point = Vector2()
//
//    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()

//    fun act(delta: Float) {
//        image.act(delta)
//        if (MovingState.ROTATE_POINT == movingState) {
//            if (isIdling()) angle += slice * speed.toFloat() * delta else angle = 0f
//            point = CalculationUtils.calculateRotationPoint(Vector2(rotationPoint.posX - rotationPoint.width / 2, rotationPoint.posY - rotationPoint.height / 2), (rotationPoint.width * 2).toDouble(), angle.toDouble())
//        }
//    }

//    fun setRotationPosition(batch: Batch?, parentAlpha: Float) {
//        if (MovingState.ROTATE_POINT == movingState && isIdling()) {
//            setRotationPosition(point.x, point.y)
//        }
//        super.setRotationPosition(batch, parentAlpha)
//    }


//    fun moveTo(rotationPoint: PositionData) {
//        var point = when (movingState) {
//            MovingState.NONE -> Vector2(rotationPoint.posX - width / 2, rotationPoint.posY - height / 2)
//            MovingState.ROTATE_POINT -> CalculationUtils.calculateRotationPoint(Vector2(rotationPoint.posX - width / 2, rotationPoint.posY - height / 2), (width * 2).toDouble())
//            MovingState.MOVING -> TODO()
//        }
//
//        this.rotationPoint = rotationPoint
//        val moveTo = MoveToAction()
//
//        moveTo.setRotationPosition(point.x, point.y)
//        moveTo.duration = (5f * speed).toFloat()
//        moveTo.duration = getDurationToTarget(rotationPoint, targetX, targetY, targetWidth, targetHeight)
//        addAction(moveTo)
//    }
}