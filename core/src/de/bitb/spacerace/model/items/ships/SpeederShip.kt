package de.bitb.spacerace.model.items.ships

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerAnimation
import de.bitb.spacerace.model.player.PlayerColor

class SpeederShip(owner: PlayerColor, price: Int) : ShipItem(owner, price) {

    private var animation: PlayerAnimation

    init {
        val landingFrame1 = TextureRegion(TextureCollection.shipLanding1)
        val landingFrame2 = TextureRegion(TextureCollection.shipLanding2)
        val animationFrame1 = TextureRegion(TextureCollection.shipMoving1)
        val animationFrame2 = TextureRegion(TextureCollection.shipMoving2)
        val animationFrame3 = TextureRegion(TextureCollection.shipMoving3)
        val animationFrame4 = TextureRegion(TextureCollection.shipMoving2)
        val movingAnimation = Animation(0.1f, animationFrame1, animationFrame2, animationFrame3, animationFrame4)
        val landingAnimation = Animation(0.3f, landingFrame1, landingFrame2)
        animation = PlayerAnimation(movingAnimation, landingAnimation)
    }

    override fun getAnimation(): BaseAnimation {
        return animation
    }

    override val itemType: ItemCollection = ItemCollection.SHIP_SPEEDER
    override val img: Texture = TextureCollection.shipLanding2
    override var text: String = ""
        get() = GameStrings.ItemStrings.SHIP_SPEEDER_TEXT


}