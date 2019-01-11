package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.blink.BlinkingImage
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage

open class GameImage(var positionData: PositionData, var img: Texture, val speed: Double = Math.random()) :
        DefaultFunction by object : DefaultFunction {},
        IMovingImage by MovingImage(),
        IBlinkingImage by BlinkingImage() {

    val image: Image = object : Image(img) {
        override fun draw(batch: Batch?, parentAlpha: Float) {
            super.draw(batch, parentAlpha)
            if (DEBUG_FIELDS) {
                val label = TextButton("IAMGE: $id", TextureCollection.skin, "default")
                label.label.width = positionData.width
                label.setPosition(positionData.posX, positionData.posY)
                label.color = Color.ROYAL
                label.style.fontColor = Color.RED
                label.draw(batch, parentAlpha)
            }
        }
    }

    open var id: Int = 0

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        image.setBounds(x, y, width, height)
        positionData.posX = image.x
        positionData.posY = image.y
        positionData.width = image.width
        positionData.height = image.height
    }

    fun setPosition(x: Float, y: Float) {
        image.setPosition(x, y)
        positionData.posX = image.x
        positionData.posY = image.y
    }

    private val actionQueue: MutableList<Action> = ArrayList()
    var movingState = MovingState.NONE

    init {
//        setBounds(x, y, width, height)
//        setOrigin(positionData.width / 2, positionData.height / 2)
    }

    var angle = 0f
    var point = Vector2()

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()

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

    fun isIdling(): Boolean {
        return image.actions.size == 0
    }

    fun addAction(action: Action?) {
        if (isIdling()) {
            image.addAction(Actions.sequence(action, getCheckAction()))
        } else {
            actionQueue.add(action!!)
        }
    }

    fun addAction(runnable: Runnable) {
        val action = RunnableAction()
        action.runnable = runnable
        addAction(action)
    }

    private fun getCheckAction(): Action {
        val check = RunnableAction()
        check.runnable = Runnable {
            @Synchronized
            if (!actionQueue.isEmpty()) {
                val seq = Actions.sequence()
                for (action in actionQueue) {
                    seq.addAction(action)
                }
                seq.addAction(getCheckAction())
                actionQueue.clear()
                image.addAction(seq)
            }
        }
        return check
    }

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