package de.bitb.spacerace.model.player

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.items.ships.RaiderShip
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage


class PlayerImage
    : GameImage(RaiderShip(PlayerColor.TEAL, 0).getAnimation()),//TODO mach das anders
        IMovingImage by MovingImage(),
        IRotatingImage by RotatingImage() {

    override var movingSpeed: Float = MOVING_SPS

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
        actRotation(this, followImage, delta)
        actMovingTo(this, followImage, delta)
    }


    override fun getBoundingRectangle(): Rectangle {
        return Rectangle(getCenterX(), getCenterY(), 10f, 10f)
    }
}
