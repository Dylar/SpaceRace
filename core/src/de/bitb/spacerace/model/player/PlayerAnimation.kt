package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState

class PlayerAnimation : TextureRegionDrawable() {

    private var stateTime = 0f
    var w1 = TextureRegion(TextureCollection.ship2)
    var w2 = TextureRegion(TextureCollection.ship1)
    var animation = Animation(0.1f, w1, w2)

    init {
        region = w1
    }

    fun actAnimation(playerImage: PlayerImage, delta: Float) {
        if (playerImage.movingState == MovingState.MOVING) {
            stateTime += delta
        }else{
            stateTime = 0f
        }
        val draw = animation.getKeyFrame(stateTime, true)
        (playerImage.drawable as TextureRegionDrawable).region = draw
    }
}