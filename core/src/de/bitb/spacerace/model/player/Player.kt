package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField

class Player(playerColor: PlayerColor = PlayerColor.NONE)
    : GameObject(PositionData()) {

    companion object {
        val NONE = Player()
    }

    var playerImage: PlayerImage = PlayerImage(TextureCollection.ship1)

    var playerData = PlayerData(playerColor)

    override fun getGameImage(): GameImage {
        return playerImage
    }

    fun setFieldPosition(spaceField: SpaceField) {
        playerImage.setFieldPosition(this, spaceField.positionData)
    }

    init {
        setBounds(positionData.posX, positionData.posY, PLAYER_BORDER, PLAYER_BORDER)
    }

}