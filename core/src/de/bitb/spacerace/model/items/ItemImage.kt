package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.TextureAnimation
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.model.player.PlayerColor

class ItemImage(val img: Texture,
                var owner: PlayerColor)
    : GameImage(TextureAnimation(img)),
        IRotatingImage by RotatingImage(),
        IMovingImage by MovingImage() {

    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    init {
        setOrigin(ITEM_BORDER / 2, ITEM_BORDER / 2)
    }

    override fun act(delta: Float) {
        super.act(delta)
        color = owner.color
        actRotation(this, followImage, delta)

    }

}
