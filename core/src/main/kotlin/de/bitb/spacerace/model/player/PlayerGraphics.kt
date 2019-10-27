package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

val NONE_PLAYER = PlayerGraphics(PlayerColor.NONE, PlayerImage(PlayerColor.NONE, ItemType.NONE_ITEM))

class PlayerGraphics(
        var playerColor: PlayerColor,
        var playerImage: PlayerImage
) : GameObject(PositionData()) {

    init {
        setBounds(gamePosition.posX, gamePosition.posY, PLAYER_BORDER, PLAYER_BORDER)
    }

    override fun getGameImage() = playerImage

}