package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable
import de.bitb.spacerace.model.objecthandling.GameImage


class AnimationDrawable(val gameImage: GameImage, val anim: Animation<Texture>) : BaseDrawable() {
    private var stateTime = 0f

    init {
        minWidth = gameImage.minWidth
        minHeight = gameImage.maxHeight
    }

    fun act(delta: Float) {
        stateTime += delta
    }

    fun reset() {
        stateTime = 0f
    }

    fun draw(batch: SpriteBatch, x: Float, y: Float, width: Float, height: Float) {
        batch.draw(anim.getKeyFrame(stateTime), x, y, width, height)
    }
}