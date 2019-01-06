package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SPEED_BOOST_TEXT
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.MultiDice
import de.bitb.spacerace.model.player.PlayerColor

class SpeedBoost(playerColor: PlayerColor, price: Int, charges: Int) : UsableItem(TextureCollection.fallingStar, playerColor, ItemCollection.SPEED_BOOST, ITEM_SPEED_BOOST_TEXT, price, charges), MultiDice {

}