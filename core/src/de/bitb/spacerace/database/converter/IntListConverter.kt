package de.bitb.spacerace.database.converter

import de.bitb.spacerace.extension.doForEachExceptLast
import io.objectbox.converter.PropertyConverter

private const val SEPERATOR = ","

class IntListConverter : PropertyConverter<MutableList<Int>, String> {

    override fun convertToDatabaseValue(entityProperty: MutableList<Int>?): String? {
        return entityProperty?.let { list ->
            var bla = ""
            list.doForEachExceptLast(
                    executeForAll = { bla += it },
                    executeForAllExceptLast = { bla += SEPERATOR })
            if (bla.isEmpty()) null else bla
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
