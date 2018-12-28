package de.bitb.spacerace.model.items.upgrade

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class IonEngine(owner: PlayerColor) : Item(owner,TextureCollection.blackhole, ITEM_ION_ENGINE_TEXT), DiceModification {
    override fun getModification(): Float {
        return 0.1f
    }

    init {
        permanent = true
    }
    override fun use(game: MainGame) {
        game.gameController.playerController.playerMap[owner]!!.playerData.diceModItems.add(this)
    }
}