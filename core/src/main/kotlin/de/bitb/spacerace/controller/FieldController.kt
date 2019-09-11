package de.bitb.spacerace.controller

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.usecase.game.action.ConnectionResult
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class FieldController
@Inject constructor(
        val graphicController: GraphicController,
        val mapDataSource: MapDataSource
) {

    var fieldsMap: MutableMap<FieldType, MutableList<FieldData>> = EnumMap(FieldType::class.java)

    init {
        MainGame.appComponent.inject(this)
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

    fun addField(fieldData: FieldData) {
        fieldsMap[fieldData.fieldType]!!.add(fieldData)
    }

//    fun setRandomGoalPosition(): Pair<PositionData?, PositionData> {
//        val oldGoal = currentGoal
//        val goals = fieldsMap[FieldType.GOAL]!!
//        currentGoal =
//                if (DEBUG_WIN_FIELD) goals.first().gamePosition
//                else goals[(Math.random() * goals.size).toInt()].gamePosition
//
//        return oldGoal to currentGoal!!
//    }

    fun setConnectionColor(connectionResult: ConnectionResult) {
        graphicController.connectionGraphics.forEach { connection ->
            connection.setColor(connectionCanBeCrossed(connection, connectionResult))
        }
    }

    fun connectionCanBeCrossed(spaceConnection: SpaceConnection, connectionResult: ConnectionResult) =
            with(connectionResult) {
                (phase == Phase.MOVE
                        && spaceConnection.isConnected(position)
                        && (stepsLeft || spaceConnection.isConnection(position, previousPosition)))
            }

}