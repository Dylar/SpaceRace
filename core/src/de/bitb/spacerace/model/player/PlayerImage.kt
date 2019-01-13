package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage

class PlayerImage(img: Texture)
    : GameImage(img),
        IMovingImage by MovingImage(),
        IRotatingImage by RotatingImage() {

    init {
        touchable = Touchable.disabled

        setOrigin(PLAYER_BORDER / 2, PLAYER_BORDER / 2)
    }

    fun setFieldPosition(player: Player, positionData: PositionData) {
        addAction(getRunnableAction(Runnable {
            player.setPosition(positionData)
        }))
    }

    override fun act(delta: Float) {
        super.act(delta)
        actRotation(this, delta)
    }

}
