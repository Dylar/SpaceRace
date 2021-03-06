package de.bitb.spacerace.grafik.model.objecthandling

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

abstract class BaseAnimation : TextureRegionDrawable() {

    abstract fun getDefaultTexture(): Texture?

    abstract fun actAnimation(gameImage: GameImage, delta: Float)

    open fun isFinished(): Boolean {
        return false
    }
}