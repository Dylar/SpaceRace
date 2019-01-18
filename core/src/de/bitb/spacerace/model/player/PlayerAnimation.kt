package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.BaseAnimation

class PlayerAnimation : BaseAnimation<PlayerImage>() {

    private var movingTime = 0f
    private var landingTime = 0f
    var landingFrame1 = TextureRegion(TextureCollection.shipLanding1)
    var landingFrame2 = TextureRegion(TextureCollection.shipLanding2)
    var animationFrame1 = TextureRegion(TextureCollection.shipMoving1)
    var animationFrame2 = TextureRegion(TextureCollection.shipMoving2)
    var animationFrame3 = TextureRegion(TextureCollection.shipMoving3)
    var animationFrame4 = TextureRegion(TextureCollection.shipMoving2)
    var movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
    var landingAnimation = Animation(0.5f, landingFrame1, landingFrame2)

    init {
        region = landingFrame1
    }

    override fun actAnimation(gameImage: PlayerImage, delta: Float) {
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