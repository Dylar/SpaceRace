package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
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
        setBounds(0f, 0f, image.width * 1.8f, image.height * 1.8f)
    }

    fun setFieldPosition(fieldPosition: SpaceField) {
        addAction(Runnable {
            setPosition(fieldPosition.positionData.posX - positionData.width / 2, fieldPosition.positionData.posY - positionData.height / 2)
        })

    }
}