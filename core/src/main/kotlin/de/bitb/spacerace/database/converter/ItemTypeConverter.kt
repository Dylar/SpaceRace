package de.bitb.spacerace.database.converter

import de.bitb.spacerace.base.JsonParser
import de.bitb.spacerace.model.items.ItemType
import io.objectbox.converter.PropertyConverter

class ItemTypeConverter : PropertyConverter<ItemType, String> {

    override fun convertToDatabaseValue(entityProperty: ItemType?) =
            entityProperty?.let { JsonParser.itemTypeParser.toJson(it) }

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { JsonParser.itemTypeParser.fromJson(it) }

}
