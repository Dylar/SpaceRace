package de.bitb.spacerace.model.items.equip

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class IonEngine(owner: PlayerColor, price: Int) : EquipItem(owner, price, TextureCollection.blackhole), DiceModification {

    override val itemType: ItemType = ItemType.ION_ENGINE
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT

    override fun getModification(): Float {
        return 0.1f
    }

}