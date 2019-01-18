package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

abstract class BaseAnimation<T : GameImage> : TextureRegionDrawable() {

    abstract fun actAnimation(gameImage: T, delta: Float)
}