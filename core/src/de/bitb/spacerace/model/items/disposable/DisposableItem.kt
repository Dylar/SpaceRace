package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(owner: PlayerColor, price: Int) : Item(owner, price) {

    val speed = Math.random()
    var attachedTo = PlayerColor.NONE

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> player != owner && getPlayerData(game, player).phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                val field = game.gameController.fieldController.getField(getPlayerPosition(game, player))
                val image = getItemImage()

                image.setRotationPoint(this, field.getGameImage(), image.followImage.width * 2.0)
                field.disposeItem(this)
                getPlayerData(game, player).playerItems.disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = player
                getPlayerData(game, player).playerItems.attachItem(this)
                game.gameController.fieldController.getField(getPlayerPosition(game, player)).attachItem(this)
                true
            }
            else -> true
        }
    }

}