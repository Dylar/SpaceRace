package de.bitb.spacerace.grafik.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.TextureAnimation
import de.bitb.spacerace.grafik.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.grafik.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.grafik.model.player.PlayerColor

class ItemImage(
        img: Texture,
        var owner: PlayerColor)
    : GameImage(),
        IRotatingImage by RotatingImage(),
        IMovingImage by MovingImage() {

    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    init {
        setOrigin(ITEM_BORDER / 2, ITEM_BORDER / 2)
        animation = TextureAnimation(img)
    }

    override fun act(delta: Float) {
        super.act(delta)
        color = owner.color
        actRotation(this, followImage, delta)
    }

}
