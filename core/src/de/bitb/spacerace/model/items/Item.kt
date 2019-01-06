package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.DefaultFunction
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor

abstract class Item(val img: Texture,
                    var owner: PlayerColor,
                    val itemType: ItemCollection,
                    val text: String,
                    val price: Int,
                    var charges: Int = 1) : DefaultFunction by object : DefaultFunction {} {
    var state: ItemState = ItemState.NONE

    abstract fun canUse(game: MainGame, player: PlayerColor): Boolean
    abstract fun use(game: MainGame, player: PlayerColor): Boolean

}
