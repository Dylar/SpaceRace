package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.items.ships.ShipItem
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import sun.audio.AudioPlayer.player

val NONE_PLAYER = PlayerGraphics(PlayerColor.NONE)

class PlayerGraphics(
        var playerColor: PlayerColor,
        val playerItems: PlayerItems = PlayerItems(playerColor),
        var playerImage: PlayerImage = PlayerImage()
) : GameObject(PositionData()) {

    init {
        setBounds(gamePosition.posX, gamePosition.posY, PLAYER_BORDER, PLAYER_BORDER)
    }

    override fun getGameImage() = playerImage

}