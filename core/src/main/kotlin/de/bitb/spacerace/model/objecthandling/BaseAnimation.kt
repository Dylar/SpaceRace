package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

abstract class BaseAnimation : TextureRegionDrawable() {

    abstract fun actAnimation(gameImage: GameImage, delta: Float)

    open fun isFinished(): Boolean {
        return false
    }
}