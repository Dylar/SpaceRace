package de.bitb.spacerace.database.converter

import de.bitb.spacerace.core.utils.doForEachExceptLast
import io.objectbox.converter.PropertyConverter

class IntListConverter : PropertyConverter<MutableList<Int>, String> {

    override fun convertToDatabaseValue(entityProperty: MutableList<Int>?): String? {
        return entityProperty?.let { list ->
            var content = ""
            list.doForEachExceptLast(
                    executeForAll = { content += it },
                    executeForAllExceptLast = { content += SEPERATOR })
            if (content.isEmpty()) null else content
        }
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<Int>? {
        return databaseValue?.split(SEPERATOR)
                ?.map { it.toInt() }
                ?.toMutableList()
                ?: mutableListOf()
    }

}
