package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_EXTRA_FUEL_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.player.PlayerColor

class ExtraFuel(playerColor: PlayerColor, price: Int) : Item(TextureCollection.ship2, playerColor, ItemCollection.EXTRA_FUEL, ITEM_EXTRA_FUEL_TEXT, price), DiceAddition {

    override fun canUse(game: MainGame): Boolean {
        return getPlayerData(game).phase.isMain1()
    }

    override fun use(game: MainGame) {
        getPlayerData(game).diceAddItems.add(this)
    }

    override fun getAddition(): Int {
        return 2
    }
}