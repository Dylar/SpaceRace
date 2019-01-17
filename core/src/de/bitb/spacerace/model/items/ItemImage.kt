package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.model.player.PlayerColor

class ItemImage(img: Texture)
    : GameImage(img),
        IRotatingImage by RotatingImage(),
        IMovingImage by MovingImage() {
    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    var itemColor: Color = PlayerColor.NONE.color

    init {
        setOrigin(ITEM_BORDER / 2, ITEM_BORDER / 2)
    }

    override fun act(delta: Float) {
        super.act(delta)
        color = itemColor
        actRotation(this, followImage, delta)
    }

}
