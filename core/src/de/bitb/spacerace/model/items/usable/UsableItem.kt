package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class UsableItem(owner: PlayerColor, price: Int, img: Texture) : Item(owner, price, img) {

    override fun canUse(game: MainGame, playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> playerData.phase.isMain()
            ItemState.USED -> playerData.phase.isMain2()//TODO muss das?
            else -> false
        }
    }

    override fun use(game: MainGame, playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerItems(game.gameController.playerController, playerData.playerColor).useItem(this)
                true
            }
            ItemState.USED -> {
                getPlayerItems(game.gameController.playerController, playerData.playerColor).removeUsedItem(this)
                true
            }
            else -> true
        }
    }

}