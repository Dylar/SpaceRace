package de.bitb.spacerace.grafik.model.player

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.objecthandling.GameObject
import de.bitb.spacerace.grafik.model.objecthandling.PositionData

val NONE_PLAYER = PlayerGraphics(PlayerColor.NONE, PlayerImage(PlayerColor.NONE, ItemType.NONE_ITEM))

class PlayerGraphics(
        var playerColor: PlayerColor,
        var playerImage: PlayerImage
) : GameObject(PositionData()) {

    val attachedItems: MutableList<ItemGraphic> = mutableListOf()

    init {
        setBounds(gamePosition.posX, gamePosition.posY, PLAYER_BORDER, PLAYER_BORDER)
    }


    override fun getGameImage() = playerImage

}