package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class UsableItemGraphic(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : ItemGraphic(owner, price, img) {

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
                graphicController.getPlayerItems(playerData.playerColor).useItem(this)
                true
            }
            ItemState.USED -> {
                graphicController.getPlayerItems(playerData.playerColor).removeUsedItem(this)
                true
            }
            else -> true
        }
    }

}