package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import io.objectbox.converter.PropertyConverter

class FieldTypeConverter : PropertyConverter<FieldType, String> {

    override fun convertToDatabaseValue(entityProperty: FieldType?): String? {
        return entityProperty?.name
    }

    override fun convertToEntityProperty(databaseValue: String?): FieldType? {
        return databaseValue?.let { FieldType.values().find { it.name == databaseValue } }
    }

}
