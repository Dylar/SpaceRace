package de.bitb.spacerace.database.converter

import de.bitb.spacerace.utils.doForEachExceptLast
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.converter.PropertyConverter

private const val SEPERATOR = ","
private const val SEPERATOR_GROUP = ";"

class PositionDataConverter : PropertyConverter<MutableList<PositionData>, String> {

    override fun convertToDatabaseValue(entityProperty: MutableList<PositionData>?): String? {
        return entityProperty?.let { list ->
            var value = ""
            list.doForEachExceptLast(
                    executeForAll = { value += "${it.posX}$SEPERATOR${it.posY}$SEPERATOR${it.width}$SEPERATOR${it.height}$SEPERATOR" },
                    executeForAllExceptLast = { value += SEPERATOR_GROUP })
            if (value.isEmpty()) null else value
        }
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<PositionData>? {
        return databaseValue?.let { value ->
            mutableListOf<PositionData>()
                    .also { list ->
                        try {
                            value.split(SEPERATOR_GROUP)
                                    .map { it.split(SEPERATOR) }
                                    .map {
                                        list.add(PositionData(it[0].toFloat(), it[1].toFloat(), it[2].toFloat(), it[3].toFloat()))
                                    }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
        } ?: mutableListOf()
    }

}
