package de.bitb.spacerace.model.items.equip

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class EquipItem(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : Item(owner, price, img) {

    override fun canUse(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == playerData.playerColor && playerData.phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(playerData: PlayerData): Boolean {
        //TODO make usecase
        return when (state) {
            ItemState.STORAGE -> {
                graphicController.getPlayerItems(playerData.playerColor).equipItem(this)
                return true
            }
            ItemState.EQUIPPED -> {
                graphicController.getPlayerItems(playerData.playerColor).unequipItem(this)
                return true
            }
            else -> false
        }
    }

}