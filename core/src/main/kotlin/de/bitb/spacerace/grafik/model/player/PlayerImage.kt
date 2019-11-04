package de.bitb.spacerace.grafik.model.player

import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.getAnimation
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.objecthandling.getRunnableAction
import de.bitb.spacerace.grafik.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.grafik.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.core.utils.Logger


class PlayerImage(
        val playerColor: PlayerColor,
        val itemType: ItemType
) : GameImage(),
        IMovingImage by MovingImage(),
        IRotatingImage by RotatingImage() {

    override var movingSpeed: Float = MOVING_SPS

    init {
        animation = itemType.getAnimation()
        touchable = Touchable.disabled
        setOrigin(PLAYER_BORDER / 2, PLAYER_BORDER / 2)
    }

    fun setFieldPosition(player: PlayerGraphics, positionData: PositionData) {
        addAction(getRunnableAction(Runnable {
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
