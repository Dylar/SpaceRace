package de.bitb.spacerace.core.controller

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditorController
@Inject constructor(
) {
    var editorMode: EditorMode = EditorMode.SELECT

    var fieldGraphics: MutableMap<PositionData, FieldGraphic> = mutableMapOf()
    val fields: MutableList<FieldConfigData> = ArrayList()
    val connections: MutableList<ConnectionGraphic> = ArrayList()

    var connectionGraphics: ConnectionList = ConnectionList()

    fun getFieldGraphic(gamePosition: PositionData) =
            fieldGraphics.keys.find { it.isPosition(gamePosition) }
                    ?.let { fieldGraphics[it] }
                    ?: NONE_SPACE_FIELD

    fun initEditor(mapData: MapData) {
        val fields = mapData.fields

        addField(fields)
        addConnections(fields)
    }

    private fun addField(fieldConfigDatas: List<FieldConfigData>) {
        fieldConfigDatas.forEach { fieldConfigData ->
            val spaceField = FieldGraphic.createField(fieldConfigData.fieldType)
                    .also { fieldGraphics[fieldConfigData.gamePosition] = it }
            fields.add(fieldConfigData)
            spaceField.getGameImage().addListener {
                when (editorMode) {
                    EditorMode.SELECT -> true
                    EditorMode.EDIT -> true
                }
            }
        }
    }

    private fun addConnections(fields: List<FieldConfigData>) {
        fields.forEach { thisField ->
            thisField.connections.forEach { thatField ->
                if (connections.none { it.isConnection(thisField.gamePosition, thatField) }) {
                    val connection = ConnectionGraphic(
                            getFieldGraphic(thisField.gamePosition),
                            getFieldGraphic(thatField))
                    connections.add(connection)
                }
            }
        }
        connectionGraphics.addAll(connections)
    }

    fun clear() {
        fieldGraphics.values.forEach{
            it.getGameImage().remove()
        }
    }

}

enum class EditorMode {
    SELECT, EDIT
}
