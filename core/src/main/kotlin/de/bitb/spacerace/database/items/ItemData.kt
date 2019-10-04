package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.converter.ItemTypeConverter
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemType
import io.objectbox.BoxStore
import io.objectbox.annotation.BaseEntity
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class ItemData(
        @Id var id: Long,
        val price: Int,
        var maxCharges: Int = 1,
        @Convert(converter = ItemTypeConverter::class, dbType = String::class)
        val itemType: ItemType
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var owner: ToOne<PlayerData> = ToOne(this, ItemData_.owner)

}
