package de.bitb.spacerace.database.converter

import de.bitb.spacerace.base.JsonParser
import de.bitb.spacerace.grafik.model.items.ItemInfo
import io.objectbox.converter.PropertyConverter

class ItemTypeConverter : PropertyConverter<ItemInfo, String> {

    override fun convertToDatabaseValue(entityProperty: ItemInfo?) =
            entityProperty?.let { JsonParser.ITEM_INFO_PARSER.toJson(it) }

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { JsonParser.ITEM_INFO_PARSER.fromJson(it) }

}
