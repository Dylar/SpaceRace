package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.converter.ItemTypeConverter
import de.bitb.spacerace.database.converter.PositionListConverter
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.items.NONE_ITEMTYPE
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class ItemData(
        @Id var id: Long = 0,
        @Convert(converter = ItemTypeConverter::class, dbType = String::class)
        val itemType: ItemType = NONE_ITEMTYPE(),
        @Convert(converter = PositionListConverter::class, dbType = String::class)
        val usablePhase: List<Phase> = listOf()
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var owner: ToOne<PlayerData> = ToOne(this, ItemData_.owner)

}
