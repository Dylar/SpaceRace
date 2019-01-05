package de.bitb.spacerace.model.items.disposable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(img: Texture, owner: PlayerColor, itemType: ItemCollection, text: String, price: Int) : Item(img, owner, itemType, text, price) {

    var attachedTo = PlayerColor.NONE

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> getPlayerData(game, player).phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerData(game, player).fieldPosition.disposedItems.add(this)
                getPlayerData(game, player).playerItems.disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = player
                getPlayerData(game, player).playerItems.attachItem(this)
                getPlayerData(game, player).fieldPosition.disposedItems.remove(this)
            }
            else -> true
        }
    }
}