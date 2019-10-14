package de.bitb.spacerace.model.items.ships

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerAnimation
import de.bitb.spacerace.model.player.PlayerColor

class RaiderShip(
        owner: PlayerColor, price: Int
) : ShipItemGraphic(owner, price, TextureCollection.raiderShipLanding2) {

    override var itemInfo: ItemInfo = ItemInfo.SHIP_RAIDER()
    override var text: String = ""
        get() = GameStrings.ItemStrings.SHIP_RAIDER_TEXT

    private var animation: PlayerAnimation

    init {
        val landingFrame1 = TextureRegion(TextureCollection.raiderShipMoving1)
        val landingFrame2 = TextureRegion(TextureCollection.raiderShipLanding1)
        val landingFrame3 = TextureRegion(TextureCollection.raiderShipLanding2)
        val animationFrame1 = TextureRegion(TextureCollection.raiderShipMoving1)
        val animationFrame2 = TextureRegion(TextureCollection.raiderShipMoving2)
        val animationFrame3 = TextureRegion(TextureCollection.raiderShipMoving3)
        val animationFrame4 = TextureRegion(TextureCollection.raiderShipMoving2)
        val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
        val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2, landingFrame3)
        animation = PlayerAnimation(movingAnimation, landingAnimation)
    }

    override fun getAnimation(): BaseAnimation {
        return animation
    }

    override fun getSpeed(): Float {
        return MOVING_SPS * 3f
    }
}