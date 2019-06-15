package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField

val NONE_PLAYER = Player(PlayerColor.NONE)

class Player(
        var playerColor: PlayerColor,
        val playerItems: PlayerItems = PlayerItems(playerColor),
        var playerImage: PlayerImage = PlayerImage()
) : GameObject(PositionData()) {

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