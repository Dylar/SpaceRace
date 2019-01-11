package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField

class Player(playerColor: PlayerColor = PlayerColor.NONE, img: Texture = TextureCollection.ship1) : GameImage(PositionData(), img) {

    companion object {
        val NONE = Player()
    }

    var playerData = PlayerData(playerColor)

    init {
        image.touchable = Touchable.disabled

        val width = PLAYER_BORDER
        val height = PLAYER_BORDER
        image.setOrigin(width / 2, height / 2)
        setBounds(positionData.posX, positionData.posY, width, height)


    }

    fun setFieldPosition(fieldPosition: SpaceField) {
        addAction(Runnable {
            setPosition(fieldPosition.positionData)
        })

    }
}