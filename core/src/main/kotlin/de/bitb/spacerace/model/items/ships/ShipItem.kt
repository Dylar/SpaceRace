package de.bitb.spacerace.model.items.ships

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerColor

abstract class ShipItem(
        owner: PlayerColor,
        price: Int,
        img: Texture
) : Item(owner, price, img) {

    abstract fun getAnimation(): BaseAnimation
    abstract fun getSpeed(): Float

    override fun canUse(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == playerData.playerColor && playerData.phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(playerData: PlayerData): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                val image = graphicController.getPlayerGraphic(playerData.playerColor).playerImage
                image.animation = getAnimation()
                image.movingSpeed = getSpeed()
                return true
            }
//            ItemState.EQUIPPED -> {
//                getPlayerData(game, player).playerItems.unequipItem(this)
//                return true
//            }
            else -> false
        }
    }

}