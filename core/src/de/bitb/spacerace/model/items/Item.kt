package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor

abstract class Item(var owner: PlayerColor,
                    val price: Int) : DefaultFunction by object : DefaultFunction {} {

    abstract val itemType: ItemCollection
    abstract val img: Texture
    abstract var text: String
    open var charges: Int = 1

    var state: ItemState = ItemState.NONE

    abstract fun canUse(game: MainGame, player: PlayerColor): Boolean
    abstract fun use(game: MainGame, player: PlayerColor): Boolean

}
