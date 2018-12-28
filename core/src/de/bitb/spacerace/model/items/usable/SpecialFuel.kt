package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SPECIAL_FUEL_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class SpecialFuel(playerColor: PlayerColor) : Item(playerColor, TextureCollection.ship1, ITEM_SPECIAL_FUEL_TEXT), DiceModification {

    override fun canUse(game: MainGame): Boolean {
        return getPlayerData(game).phase.isMain1()
    }

    override fun use(game: MainGame) {
        getPlayerData(game).diceModItems.add(this)
    }

    override fun getModification(): Float {
        return 0.3f
    }
}