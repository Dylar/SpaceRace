package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_EXTRA_FUEL_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.player.PlayerColor

class ExtraFuel(playerColor: PlayerColor) : Item(playerColor, TextureCollection.ship2, ITEM_EXTRA_FUEL_TEXT), DiceAddition {

    override fun canUse(game: MainGame): Boolean {
        return game.gameController.playerController.getPlayer(owner).playerData.phase.isMain1()
    }

    override fun use(game: MainGame) {
        game.gameController.playerController.playerMap[owner]!!.playerData.diceAddItems.add(this)
    }

    override fun getAddition(): Int {
        return 2
    }
}