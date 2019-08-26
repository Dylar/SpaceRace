package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.model.items.disposable.moving.MovingState

abstract class GameImage(
        var animation: BaseAnimation = TextureAnimation()
) : Image() {

    companion object {
        val NONE: GameImage = object : GameImage() {
            override var movingSpeed: Float = (MOVING_SPS * Math.random()).toFloat()
        }
    }

    var boundingRectangle: Rectangle = Rectangle(0f, 0f, 10f, 10f)
        get() {
            field.x = getCenterX()
            field.y = getCenterY()
            field.width = 10f
            field.height = 10f
            return field
        }

    var movingState: MovingState = MovingState.NONE

    var targetPoint: Rectangle? = null
    var followImage: GameImage = NONE

    abstract var movingSpeed: Float
    var idlingCount = 0

    private val actionQueue: MutableList<Action> = ArrayList()
        //TODO maybe
        @Synchronized get


    init {
        drawable = animation
        debug = DEBUG_LAYOUT
    }

    fun getCenterX(): Float {
        return x + width / 2
    }

    fun getCenterY(): Float {
        return y + width / 2
    }

    fun setCenterX(posX: Float) {
        x = posX - width / 2
    }

    fun setCenterY(posY: Float) {
        x = posY - height / 2
    }

    fun isIdling(): Boolean {
        return actions.size == idlingCount
    }

    override fun addAction(action: Action?) {
        addAction(action!!)
    }

    fun addAction(vararg actions: Action) {
        if (actions.isNotEmpty()) {
            actionQueue.add(Actions.sequence(*actions))
        }
    }

    override fun act(delta: Float) {
        super.act(delta)
        animation.actAnimation(this, delta)
        if (isIdling() && actionQueue.isNotEmpty()) {
            val seq = Actions.sequence(*actionQueue.toTypedArray())
            actionQueue.clear()
            super.addAction(seq)
        }
    }
}
