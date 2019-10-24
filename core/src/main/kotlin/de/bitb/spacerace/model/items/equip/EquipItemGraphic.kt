package de.bitb.spacerace.model.items.equip

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class EquipItemGraphic(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : ItemGraphic(owner, price, img) {

    override fun canUse(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == playerData.playerColor && playerData.phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(playerData: PlayerData): Boolean {
        //TODO delete me
        return true
    }

}