package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.items.ItemType
import io.objectbox.converter.PropertyConverter

class ItemTypeConverter : PropertyConverter<ItemType, String> {

    override fun convertToDatabaseValue(entityProperty: ItemType?) =
            entityProperty?.let {
                
                it::class.objectInstance.toString()
            }

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { ItemType.getAll().find { it.name == databaseValue } }

}
