package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData

abstract class Item(val owner: PlayerColor, val img: Texture, val text: String) {
    var used = false

    fun getDisplayImage(): Image {
        return object : Image(img) {}
    }

    fun getPlayerData(game: MainGame): PlayerData {
        return game.gameController.playerController.getPlayer(owner).playerData
    }

    abstract fun canUse(game: MainGame): Boolean
    abstract fun use(game: MainGame)

}
