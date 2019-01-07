package de.bitb.spacerace.model.items.equip

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class IonEngine(owner: PlayerColor, price: Int) : EquipItem(owner, price), DiceModification {

    override val itemType: ItemCollection = ItemCollection.ION_ENGINE
    override val img: Texture = TextureCollection.blackhole
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_ION_ENGINE_TEXT

    override fun getModification(): Float {
        return 0.1f
    }

}