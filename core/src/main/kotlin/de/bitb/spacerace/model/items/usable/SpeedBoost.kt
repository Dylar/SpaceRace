package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class SpeedBoost(
        playerColor: PlayerColor,
        price: Int,
        img: Texture = TextureCollection.fallingStar
) : UsableItemGraphic(playerColor, price, img) {

    override var itemInfo: ItemInfo = ItemInfo.SPEED_BOOST()
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_SPEED_BOOST_TEXT

}