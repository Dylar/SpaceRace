package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.converter.ItemTypeConverter
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.NoneItem
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class ItemData(
        @Id var id: Long = 0,
        @Convert(converter = ItemTypeConverter::class, dbType = String::class)
        val itemInfo: ItemInfo = NoneItem()
) {
    companion object {
        fun createItem(player: PlayerData, itemInfo: ItemInfo) =
                ItemData(itemInfo = itemInfo).apply { owner.target = player }
    }

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var owner: ToOne<PlayerData> = ToOne(this, ItemData_.owner)

    override fun hashCode(): Int = id.toInt()

    override fun equals(other: Any?): Boolean =
            when (other) {
                is ItemData -> itemInfo.type == other.itemInfo.type
                else -> false
            }
}
