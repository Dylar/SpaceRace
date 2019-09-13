package de.bitb.spacerace.model.player

import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.getRunnableAction
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.utils.Logger


class PlayerImage
    : GameImage(),//TODO mach das anders //RaiderShip(PlayerColor.TEAL, 0).getAnimation()
        IMovingImage by MovingImage(),
        IRotatingImage by RotatingImage() {

    override var movingSpeed: Float = MOVING_SPS

    init {
        touchable = Touchable.disabled
        setOrigin(PLAYER_BORDER / 2, PLAYER_BORDER / 2)
    }

    fun setFieldPosition(player: PlayerGraphics, positionData: PositionData) {
        addAction(getRunnableAction(Runnable {
            Logger.println("setFieldPosition")
            player.setPosition(positionData)
            x = positionData.posX
            y = positionData.posY
        }))
    }

    override fun act(delta: Float) {
        super.act(delta)
        actRotation(this, followImage, delta)
        actMovingTo(delta, this, followImage)
    }

}
