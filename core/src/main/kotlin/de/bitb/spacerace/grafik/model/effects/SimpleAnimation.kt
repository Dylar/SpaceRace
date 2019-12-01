package de.bitb.spacerace.grafik.model.effects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.grafik.model.objecthandling.BaseAnimation
import de.bitb.spacerace.grafik.model.objecthandling.GameImage


class SimpleAnimation(
        private val eachFrame: Float = 0.1f,
        private val pauseTime: Float = 2f,
        vararg textures: TextureRegion
) : BaseAnimation() {

    override fun getDefaultTexture(): Texture? = animation.keyFrames?.firstOrNull()?.texture

    private var animationTime = 0f

    private var animation: Animation<TextureRegion> = Animation(eachFrame, *textures)

    init {
        region = animation.keyFrames.last()
    }

    override fun actAnimation(gameImage: GameImage, delta: Float) {
        animationTime += delta
        (gameImage.drawable as TextureRegionDrawable).region = animation.getKeyFrame(animationTime, false)

        if (isFinished()) {
            animationTime = 0f
        }
    }

    override fun isFinished(): Boolean {
        return eachFrame * animation.keyFrames.size + pauseTime < animationTime
    }
}