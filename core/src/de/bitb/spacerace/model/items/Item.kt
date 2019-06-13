package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.annotation.Entity


abstract class Item(
        var owner: PlayerColor,
        val price: Int,
        val img: Texture
) : GameObject() {

    abstract val itemType: ItemCollection
    abstract var text: String
    open var charges: Int = 1

    var state: ItemState = ItemState.NONE

    var itemImage = ItemImage(img, owner)

    override fun getGameImage(): GameImage {
        return itemImage
    }

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
    }

    abstract fun canUse(game: MainGame, player: PlayerColor): Boolean
    abstract fun use(game: MainGame, player: PlayerColor): Boolean

}
