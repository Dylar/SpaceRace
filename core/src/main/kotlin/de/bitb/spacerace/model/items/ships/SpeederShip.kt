package de.bitb.spacerace.model.items.ships

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerAnimation
import de.bitb.spacerace.model.player.PlayerColor

class SpeederShip(owner: PlayerColor, price: Int) : ShipItem(owner, price, TextureCollection.speederShipLanding2) {

    private var animation: PlayerAnimation

    init {
        val landingFrame1 = TextureRegion(TextureCollection.speederShipMoving1)
        val landingFrame2 = TextureRegion(TextureCollection.speederShipLanding1)
        val landingFrame3 = TextureRegion(TextureCollection.speederShipLanding2)
        val animationFrame1 = TextureRegion(TextureCollection.speederShipMoving1)
        val animationFrame2 = TextureRegion(TextureCollection.speederShipMoving2)
        val animationFrame3 = TextureRegion(TextureCollection.speederShipMoving3)
        val animationFrame4 = TextureRegion(TextureCollection.speederShipMoving2)
        val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
        val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
        animation = PlayerAnimation(movingAnimation, landingAnimation)
    }

    override fun getAnimation(): BaseAnimation {
        return animation
    }

    override fun getSpeed(): Float {
        return MOVING_SPS * 1.3f
    }

    override val itemType: ItemType = ItemType.SHIP_RAIDER()
    override var text: String = ""
        get() = GameStrings.ItemStrings.SHIP_SPEEDER_TEXT


}