package de.bitb.spacerace.model.items.equip

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class EquipItem(owner: PlayerColor, price: Int, img: Texture) : Item(owner, price, img) {

    override fun canUse(game: MainGame, playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == playerData.playerColor && playerData.phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(game: MainGame, playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerItems(game, playerData.playerColor).equipItem(this)
                return true
            }
            ItemState.EQUIPPED -> {
                getPlayerItems(game, playerData.playerColor).unequipItem(this)
                return true
            }
            else -> false
        }
    }

}