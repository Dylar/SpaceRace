package de.bitb.spacerace.ui.screens.editor

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import de.bitb.spacerace.base.getWorldInputCoordination
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.ui.base.addClickListener
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EditorGloc
@Inject constructor(
        private val editorBloc: EditorBloc,
        val observeEditorModeUseCase: ObserveEditorModeUseCase,
        val observeSelectedEntityUseCase: ObserveSelectedEntityUseCase
) {

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
            spaceField.setPosition(fieldConfigData.gamePosition)
            fieldGraphics[fieldConfigData.gamePosition] = spaceField
            fields.add(fieldConfigData)
            val gameImage = spaceField.getGameImage()
            gameImage.addClickListener(
                    onClick = { editorBloc.selectEntity(fieldConfigData) },
                    longClick = { editorBloc.onLongClickField(fieldConfigData) }
            )
            gameImage.addListener(object : DragListener() {
                override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                    if (editorBloc.isDragMode()) {
                        val input = getWorldInputCoordination(gameImage.stage.camera)
                        gameImage.setPosition(input.posX, input.posY)
                    }
                }
            })
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
//        fieldGraphics.values.forEach { it.getGameImage().remove() }
//        connectionGraphics.remove()

        fieldGraphics.clear()
        fields.clear()
        connectionGraphics.clear()
        connections.clear()
    }

    fun getCenterOnEntity(): GameImage? = connectionGraphics.firstOrNull()?.spaceField1?.fieldImage
    fun setMode(mode: EditorMode) {
        Logger.justPrint("OH NO MODE: $mode")
        editorBloc.editorMode = mode
    }

}

