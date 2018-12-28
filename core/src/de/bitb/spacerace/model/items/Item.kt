package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor

abstract class Item(val owner: PlayerColor, val img: Texture, val text: String) {
    var permanent = false

    fun getDisplayImage(): Image {
        return object : Image(img) {}
    }

    abstract fun use(game: MainGame)

}
