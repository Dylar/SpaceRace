package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.player.PlayerColor
import javax.inject.Inject

abstract class ItemGraphic(
        var owner: PlayerColor,
        val price: Int,
        val img: Texture
) : GameObject() {

    @Inject
    protected lateinit var graphicController: GraphicController
    
    abstract var itemInfo: ItemInfo
    abstract var text: String
    open var charges: Int = 1

    var state: ItemState = ItemState.NONE

    var itemImage = ItemImage(img, owner)

    override fun getGameImage(): GameImage {
        return itemImage
    }

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
        MainGame.appComponent.inject(this)
    }

    abstract fun canUse(playerData: PlayerData): Boolean
    abstract fun use(playerData: PlayerData): Boolean

}
