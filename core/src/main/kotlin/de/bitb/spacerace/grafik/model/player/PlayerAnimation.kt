package de.bitb.spacerace.grafik.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.grafik.model.items.disposable.moving.MovingState
import de.bitb.spacerace.grafik.model.objecthandling.BaseAnimation
import de.bitb.spacerace.grafik.model.objecthandling.GameImage

class PlayerAnimation(
        private var movingAnimation: Animation<TextureRegion>,
        private var landingAnimation: Animation<TextureRegion>
) : BaseAnimation() {

    private var movingTime = 0f
    private var landingTime = 0f

    init {
        region = getFirstImage()
    }

    fun getFirstImage(): TextureRegion? = landingAnimation.keyFrames.first()
    fun getLastImage(): TextureRegion? = movingAnimation.keyFrames.last()
    override fun getDefaultTexture(): Texture? = getFirstImage()?.texture

    override fun actAnimation(gameImage: GameImage, delta: Float) {
        val draw = when (gameImage.movingState) {
            MovingState.MOVING -> {
                movingTime += delta
                landingTime = 0f
                movingAnimation.getKeyFrame(movingTime, true)
            }
            else -> {
                landingTime += delta
                movingTime = 0f
                landingAnimation.getKeyFrame(landingTime, false)
            }
        }
        (gameImage.drawable as TextureRegionDrawable).region = draw
    }
}