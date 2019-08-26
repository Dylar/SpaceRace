package de.bitb.spacerace.model.items.usable.clean

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.usable.UsableItem
import de.bitb.spacerace.model.objecthandling.getPlayerItems
import de.bitb.spacerace.model.player.PlayerColor

abstract class CleanItem(playerColor: PlayerColor,
                         price: Int,
                         img: Texture
) : UsableItem(playerColor, price, img) {

    val cleanable = ArrayList<ItemCollection>()

    override fun use(playerData: PlayerData): Boolean {
        if (state.isStorage()) {
            graphicController.getPlayerItems(playerData.playerColor).detachItems(cleanable)
        }
        return super.use(playerData)
    }

}