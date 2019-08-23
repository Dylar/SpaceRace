package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.enums.Phase
import io.objectbox.converter.PropertyConverter

class PhaseConverter : PropertyConverter<Phase, String> {

    override fun convertToDatabaseValue(entityProperty: Phase?) =
            entityProperty?.name

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { Phase.values().find { it.name == databaseValue } }

}
