package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage

class ItemImage(img: Texture, var rotationPoint: PositionData = PositionData()) : GameImage(img),
        IRotatingImage by RotatingImage(),
        IMovingImage by MovingImage() {
    var movingState: MovingState = MovingState.NONE

    init {
        setOrigin(ITEM_BORDER / 2, ITEM_BORDER / 2)
    }

    override fun act(delta: Float) {
        super.act(delta)
        when (movingState) {
            MovingState.ROTATE_POINT -> {
                setPosition(this, rotationPoint, delta)
            }
            MovingState.MOVING -> {
            }
            MovingState.NONE -> {
            }
        }
    }

}
