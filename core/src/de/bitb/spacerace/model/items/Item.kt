package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerImage

abstract class Item(var owner: PlayerColor,
                    val price: Int) : GameObject() {

    abstract val itemType: ItemCollection
    abstract val img: Texture
    abstract var text: String
    open var charges: Int = 1

    var state: ItemState = ItemState.NONE
    private lateinit var itemImage: ItemImage

    override fun getGameImage(): GameImage {
        return getItemImage()
    }

    fun getItemImage(): ItemImage {
        return if (::itemImage.isInitialized) itemImage else {
            itemImage = ItemImage(img)
            itemImage
        }
    }

    abstract fun canUse(game: MainGame, player: PlayerColor): Boolean
    abstract fun use(game: MainGame, player: PlayerColor): Boolean

}
