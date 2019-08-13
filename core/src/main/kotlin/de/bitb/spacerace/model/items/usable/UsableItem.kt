package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class UsableItem(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : Item(owner, price, img) {

    override fun canUse(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> playerData.phase.isMain()
            ItemState.USED -> playerData.phase.isMain2()//TODO muss das?
            else -> false
        }
    }

    override fun use(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerItems(playerController, playerData.playerColor).useItem(this)
                true
            }
            ItemState.USED -> {
                getPlayerItems(playerController, playerData.playerColor).removeUsedItem(this)
                true
            }
            else -> true
        }
    }

}