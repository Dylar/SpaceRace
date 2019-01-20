package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.model.items.disposable.moving.MovingState

abstract class GameImage(var animation: BaseAnimation = TextureAnimation()) : Image(), DefaultFunction by object : DefaultFunction {} {
    companion object {
        val NONE: GameImage = object : GameImage() {
            override var movingSpeed: Float = (MOVING_SPS * Math.random()).toFloat()
        }
    }

    var movingState: MovingState = MovingState.NONE
    var followImage: GameImage = NONE
    abstract var movingSpeed: Float
    var idlingCount = 0

    private val actionQueue: MutableList<Action> = ArrayList()
        @Synchronized get

    init {
        drawable = animation
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
        actionQueue.add(Actions.sequence(*actions))
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

    open fun getBoundingRectangle(): Rectangle {
        return Rectangle(getCenterX(), getCenterY(), 100f, 100f)
    }//TODO Object poooool!
}
