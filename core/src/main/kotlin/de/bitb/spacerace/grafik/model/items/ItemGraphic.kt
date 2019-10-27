package de.bitb.spacerace.grafik.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.GameObject
import de.bitb.spacerace.grafik.model.player.PlayerColor
import javax.inject.Inject

open class ItemGraphic(
        var owner: PlayerColor,
        var itemType: ItemType,
        img: Texture
) : GameObject() {

    @Inject
    protected lateinit var graphicController: GraphicController
    
    var itemImage = ItemImage(img, owner)

    override fun getGameImage(): GameImage {
        return itemImage
    }

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
        MainGame.appComponent.inject(this)
    }

}
