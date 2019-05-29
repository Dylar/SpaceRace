package de.bitb.spacerace.database.converter

import de.bitb.spacerace.extension.doForEachExceptLast
import io.objectbox.converter.PropertyConverter

private const val SEPERATOR = ","

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
        return databaseValue?.let { value ->
            mutableListOf<Int>()
                    .also { list ->
                        try {
                            //TODO
                            value.split(SEPERATOR).forEach { list.add(it.toInt()) }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
        } ?: mutableListOf()
    }

}
