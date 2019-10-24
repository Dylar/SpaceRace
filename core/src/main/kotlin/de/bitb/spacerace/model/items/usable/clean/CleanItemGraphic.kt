package de.bitb.spacerace.model.items.usable.clean

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.items.usable.UsableItemGraphic
import de.bitb.spacerace.model.player.PlayerColor

abstract class CleanItemGraphic(playerColor: PlayerColor,
                                price: Int,
                                img: Texture
) : UsableItemGraphic(playerColor, price, img) {

    val cleanable = ArrayList<ItemInfo>()

    override fun use(playerData: PlayerData): Boolean {
        if (state.isStorage()) {//TODO delete this
//            graphicController.getPlayerItems(playerData.playerColor).detachItems(cleanable)
        }
        return super.use(playerData)
    }

}