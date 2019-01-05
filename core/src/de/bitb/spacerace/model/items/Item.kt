package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData

abstract class Item(val img: Texture,
                    var owner: PlayerColor,
                    val itemType: ItemCollection,
                    val text: String,
                    val price: Int) {
    var state: ItemState = ItemState.NONE

    fun getDisplayImage(): Image {
        return object : Image(img) {}
    }//TODO do this in baseObject (or something .. kotlin delegate u know)

    fun getPlayerData(game: MainGame, playerColor: PlayerColor = owner): PlayerData {
        return game.gameController.playerController.getPlayer(playerColor).playerData
    }

    abstract fun canUse(game: MainGame, player: PlayerColor): Boolean
    abstract fun use(game: MainGame, player: PlayerColor): Boolean

}
