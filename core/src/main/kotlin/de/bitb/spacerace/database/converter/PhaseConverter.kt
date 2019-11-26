package de.bitb.spacerace.database.converter

import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.core.utils.doForEachExceptLast
import io.objectbox.converter.PropertyConverter

class PhaseListConverter : PropertyConverter<MutableList<Phase>, String> {

    override fun convertToDatabaseValue(entityProperty: MutableList<Phase>?): String? {
        return entityProperty?.let { list ->
            var value = ""
            list.doForEachExceptLast(
                    executeForAll = { value += parsePhase(it) },
                    executeForAllExceptLast = { value += SEPERATOR_GROUP })
            if (value.isEmpty()) null else value
        }
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<Phase>? =
            databaseValue
                    ?.split(SEPERATOR_GROUP)
                    ?.map { parsePhaseString(it) }
                    ?.toMutableList()
}

class PhaseConverter : PropertyConverter<Phase, String> {

    override fun convertToDatabaseValue(entityProperty: Phase?): String? =
            entityProperty?.let { parsePhase(it) }

    override fun convertToEntityProperty(databaseValue: String?): Phase? =
            databaseValue?.let { parsePhaseString(it) }

}

private fun parsePhase(phase: Phase): String = phase.name
private fun parsePhaseString(dbString: String): Phase =
        Phase.values().find { it.name == dbString } ?: Phase.MAIN1

