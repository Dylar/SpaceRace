package de.bitb.spacerace.model.items.equip

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class IonEngine(owner: PlayerColor, price: Int) : EquipItemGraphic(owner, price, TextureCollection.blackhole) {

    override val itemInfo: ItemInfo = ItemInfo.ION_ENGINE()
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT

}