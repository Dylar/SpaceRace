package de.bitb.spacerace.model.items.disposable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItemGraphic(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : ItemGraphic(owner, price, img) {

    val speed = Math.random()
    var attachedTo = PlayerColor.NONE

    override fun canUse(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> playerData.phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> playerData.playerColor != owner && playerData.phase.isMain2()
            else -> false
        }
    }

    override fun use(playerData: PlayerData): Boolean {
        val playerColor = playerData.playerColor
        return when (state) {
            ItemState.STORAGE -> {
                val field = graphicController.getPlayerFieldGraphic( playerColor)
                val fieldImage = field.getGameImage()
                this.itemImage.setRotating(this, fieldImage, fieldImage.width * 0.7)
                field.disposeItem(this)
                graphicController.getPlayerItems(playerColor).disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = playerColor
                val playerImage = graphicController.getPlayerGraphic(playerColor).playerImage
                graphicController.getPlayerItems(playerColor).attachItem(this)
                graphicController.getPlayerFieldGraphic(playerColor)
//                fieldController.getField(graphicController.getPlayerPosition(playerColor)).attachItem(this)
                this.itemImage.setRotating(this, playerImage, playerImage.width * 0.7)
                true
            }
            else -> true
        }
    }

}