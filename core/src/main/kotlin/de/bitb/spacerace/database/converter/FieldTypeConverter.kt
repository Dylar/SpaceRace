package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.enums.FieldType
import io.objectbox.converter.PropertyConverter

class FieldTypeConverter : PropertyConverter<FieldType, String> {

    override fun convertToDatabaseValue(entityProperty: FieldType?) =
            entityProperty?.name

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { FieldType.values().find { it.name == databaseValue } }

}
