package de.bitb.spacerace.model.items.upgrade

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.PlayerColor

abstract class UpgradeItem(owner: PlayerColor, img: Texture, text: String) : Item(owner, img, text) {

    abstract fun stopUsing(game: MainGame)
}