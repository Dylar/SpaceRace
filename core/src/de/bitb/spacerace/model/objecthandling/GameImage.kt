package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState

abstract class GameImage(val texture: Texture) : Image(texture), DefaultFunction by object : DefaultFunction {} {
    companion object {
        val NONE: GameImage = object : GameImage(TextureCollection.fallingStar) {}
    }

    var followImage: GameImage = NONE
    var idlingCount = 0

    private val actionQueue: MutableList<Action> = ArrayList()
        @Synchronized get

    var movingState: MovingState = MovingState.NONE
    val imagePosition: PositionData = PositionData()
        get() {
            field.posY = y
            field.posX = x
            field.width = width
            field.height = height
            return field
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

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (DEBUG_FIELDS) {
            val label = TextButton("IAMGE: MACH DIE NUMMER", TextureCollection.skin, "default")
            label.label.width = width
            label.setPosition(x, y)
            label.color = Color.ROYAL
            label.style.fontColor = Color.RED
            label.draw(batch, parentAlpha)
        }
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
        if (isIdling() && actionQueue.isNotEmpty()) {
            val seq = Actions.sequence(*actionQueue.toTypedArray())
            actionQueue.clear()
            super.addAction(seq)
        }
    }

    open fun getBoundingRectangle(): Rectangle {
        return Rectangle(getCenterX(), getCenterY(), 100f, 100f)
    }
}
