package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField

class Player(
        var playerData: PlayerData,
        var playerImage: PlayerImage = PlayerImage()
) : GameObject(PositionData()) {

    companion object {
        val NONE = Player(PlayerData())
    }

    init {
        setBounds(gamePosition.posX, gamePosition.posY, PLAYER_BORDER, PLAYER_BORDER)
    }

    override fun getGameImage(): GameImage {
        return playerImage
    }

    fun setFieldPosition(spaceField: SpaceField) {
        playerImage.setFieldPosition(this, spaceField.gamePosition)
    }

}