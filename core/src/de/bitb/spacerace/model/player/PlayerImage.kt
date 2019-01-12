package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.blink.BlinkingImage
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerImage(img: Texture, playerColor: PlayerColor)
    : GameImage(img), IMovingImage by MovingImage() {

    init {
        touchable = Touchable.disabled

        val width = Dimensions.GameDimensions.PLAYER_BORDER
        val height = Dimensions.GameDimensions.PLAYER_BORDER
        setOrigin(width / 2, height / 2)
    }

    fun setFieldPosition(player: Player, positionData: PositionData) {
        addAction(Runnable {
            player.setPosition(positionData)
        })
    }

}
