package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.utils.doForEachExceptLast
import io.objectbox.converter.PropertyConverter

private const val SEPERATOR = ","
private const val SEPERATOR_GROUP = ";"

class PositionListConverter : PropertyConverter<MutableList<PositionData>, String> {

    override fun convertToDatabaseValue(entityProperty: MutableList<PositionData>?): String? {
        return entityProperty?.let { list ->
            var value = ""
            list.doForEachExceptLast(
                    executeForAll = { value += parsePositionData(it) },
                    executeForAllExceptLast = { value += SEPERATOR_GROUP })
            if (value.isEmpty()) null else value
        }
    }

    override fun convertToEntityProperty(databaseValue: String?): MutableList<PositionData>? {
        return databaseValue?.let { value ->
            mutableListOf<PositionData>()
                    .also { list ->
                        value.split(SEPERATOR_GROUP)
                                .map { list.add(parseString(it)) }
                    }
        } ?: mutableListOf()
    }

}

class PositionDataConverter : PropertyConverter<PositionData, String> {

    override fun convertToDatabaseValue(entityProperty: PositionData?): String? {
        return entityProperty?.let {
            parsePositionData(it)
        } ?: ""
    }

    override fun convertToEntityProperty(databaseValue: String?): PositionData? {
        return databaseValue?.let {
            parseString(it)
        } ?: PositionData()
    }

}

private fun parsePositionData(positionData: PositionData): String =
        "${positionData.posX}$SEPERATOR${positionData.posY}$SEPERATOR${positionData.width}$SEPERATOR${positionData.height}$SEPERATOR"

private fun parseString(it: String): PositionData =
        it.split(SEPERATOR).let { split ->
            PositionData(split[0].toFloat(),
                    split[1].toFloat(),
                    split[2].toFloat(),
                    split[3].toFloat())
        }
