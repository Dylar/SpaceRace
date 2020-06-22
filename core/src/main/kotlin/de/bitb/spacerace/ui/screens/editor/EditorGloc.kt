package de.bitb.spacerace.ui.screens.editor

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.ui.base.addClickListener
import de.bitb.spacerace.usecase.DisposableHandler
import io.objectbox.relation.ToMany
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EditorGloc
@Inject constructor(
        val editorBloc: EditorBloc,
        val addEntityToMapDispenser: AddEntityToMapDispenser,
        val observeEditorModeUseCase: ObserveEditorModeUseCase,
        val observeSelectedEntityUseCase: ObserveSelectedEntityUseCase,
        val observeAddEntityUseCase: ObserveAddEntityUseCase,
        val observeAddEntityToMapUseCase: ObserveAddEntityToMapUseCase
) : DisposableHandler {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var fieldGraphics: MutableMap<FieldGraphic, FieldConfigData> = mutableMapOf()

    private val connectionList: ConnectionList = ConnectionList()

    private fun getFieldGraphic(gamePosition: PositionData): FieldGraphic {
        val key = fieldGraphics.keys.firstOrNull { it.gamePosition.isPosition(gamePosition) }
        return key ?: NONE_SPACE_FIELD
    }

    fun initEditor(mapData: MapData) {
        val fields = mapData.fields

        initFields(fields)
        initConnection(*fields.toTypedArray())
        observeGloc()
    }

    private fun initFields(fields: ToMany<FieldConfigData>) {
        fields.forEach { fieldConfigData -> addField(fieldConfigData) }
    }

    private fun initConnection(vararg fields: FieldConfigData) {
        fields.forEach { thisField ->
            thisField.connections.forEach { thatField ->
                if (connectionList.connections.none { it.isConnection(thisField.gamePosition, thatField) }) {
                    val connection = ConnectionGraphic(
                            getFieldGraphic(thisField.gamePosition),
                            getFieldGraphic(thatField))
                    connectionList.connections.add(connection)
                }
            }
        }
        addEntityToMapDispenser.publishUpdate(AddEntityToMap.AddConnectionsToMap(connectionList))
    }

    private fun observeGloc() {
        observeAddEntityUseCase
                .observeStream {
                    when (it) {
                        is AddEntity.AddField -> {
                            val fieldConfigData = it.fieldConfigData
                            addField(fieldConfigData)
                        }
                        is AddEntity.AddConnection -> {
                            addConnection(it.field1, it.field2)
                        }
                    }
                }
                .addDisposable()
    }

    private fun addConnection(field1: FieldConfigData, field2: FieldConfigData) {
        val spaceField1 = getFieldGraphic(field1.gamePosition)
        val spaceField2 = getFieldGraphic(field2.gamePosition)
        connectionList.connections.add(ConnectionGraphic(spaceField1, spaceField2))
    }

    private fun addField(fieldConfigData: FieldConfigData) {
        val spaceField = FieldGraphic.createFieldOLD(fieldConfigData.fieldType)
        spaceField.setPosition(fieldConfigData.gamePosition)

        fieldGraphics[spaceField] = fieldConfigData

        val gameImage = spaceField.getGameImage()
        gameImage.addClickListener(
                onClick = { editorBloc.selectEntity(fieldConfigData) },
                longClick = { editorBloc.connectToField(fieldConfigData) }
        )
        gameImage.addListener(object : DragListener() {
            override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                editorBloc.updateFieldPosition(gameImage.stickToCursor())
            }
        })
        addEntityToMapDispenser.publishUpdate(AddEntityToMap.AddFieldToMap(spaceField))
    }

    fun dispose() {
//        fieldGraphics.values.forEach { it.getGameImage().remove() }
//        connectionGraphics.remove()

        fieldGraphics.clear()
        connectionList.clear()
    }

    fun getStartField(): GameImage? = connectionList.connections.firstOrNull()?.spaceField1?.fieldImage

    fun changModeClicked(mode: EditorMode) {
        editorBloc.editorMode = mode
    }

    fun addEntityClicked(fieldType: FieldType) {
        editorBloc.addEntity(fieldType)
    }

}

