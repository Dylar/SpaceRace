package de.bitb.spacerace.model.items.disposable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(owner: PlayerColor, price: Int, img: Texture) : Item(owner, price, img) {

    val speed = Math.random()
    var attachedTo = PlayerColor.NONE

    override fun canUse(game: MainGame, playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> playerData.phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> playerData.playerColor != owner && playerData.phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, playerData: PlayerData): Boolean {
        val playerColor = playerData.playerColor
        return when (state) {
            ItemState.STORAGE -> {
                val field = getPlayerField(game.gameController.fieldController, playerColor)
                val fieldImage = field.getGameImage()
                this.itemImage.setRotating(this, fieldImage, fieldImage.width * 0.7)
                field.disposeItem(this)
                getPlayerItems(game.gameController.playerController, playerColor).disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = playerColor
                val playerImage = getPlayerImage(game.gameController.playerController, playerColor)
                getPlayerItems(game.gameController.playerController, playerColor).attachItem(this)
                game.gameController.fieldController.getField(getPlayerPosition(game.gameController.playerController, playerColor)).attachItem(this)
                this.itemImage.setRotating(this, playerImage, playerImage.width * 0.7)
                true
            }
            else -> true
        }
    }

}