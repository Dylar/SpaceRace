package de.bitb.spacerace.model.items.equip

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class IonEngine(owner: PlayerColor, price: Int) : EquipItem(TextureCollection.blackhole, owner, ItemCollection.ION_ENGINE, ITEM_ION_ENGINE_TEXT, price), DiceModification {

    override fun getModification(): Float {
        return 0.1f
    }

}