package de.bitb.spacerace.model.items.ships

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerAnimation
import de.bitb.spacerace.model.player.PlayerColor

class BumperShip(owner: PlayerColor, price: Int) : ShipItem(owner, price, TextureCollection.bumperShipLanding2), DiceModification {

    override val itemType: ItemCollection = ItemCollection.SHIP_BUMPER
    override var text: String = ""
        get() = GameStrings.ItemStrings.SHIP_BUMPER_TEXT

    private var animation: PlayerAnimation

    init {
        val landingFrame1 = TextureRegion(TextureCollection.bumperShipMoving1)
        val landingFrame2 = TextureRegion(TextureCollection.bumperShipLanding1)
        val landingFrame3 = TextureRegion(TextureCollection.bumperShipLanding2)
        val animationFrame1 = TextureRegion(TextureCollection.bumperShipMoving1)
        val animationFrame2 = TextureRegion(TextureCollection.bumperShipMoving2)
        val animationFrame3 = TextureRegion(TextureCollection.bumperShipMoving3)
        val animationFrame4 = TextureRegion(TextureCollection.bumperShipMoving2)
        val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
        val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
        animation = PlayerAnimation(movingAnimation, landingAnimation)
    }

    override fun getAnimation(): BaseAnimation {
        return animation
    }

    override fun getSpeed(): Float {
        return MOVING_SPS * 0.7f
    }

    override fun getModification(): Float {
        return -0.1f
    }

}