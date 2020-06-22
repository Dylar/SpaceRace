package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.map.NONE_FIELD_CONFIG
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditorBloc
@Inject constructor(
        private val modeSubject: EditorModeDispenser,
        private val selectedEntitySubject: SelectedEntityDispenser,
        private val addEntitySubject: AddEntityDispenser
) {
    lateinit var loadedMap: MapData

    var editorMode: EditorMode
        get() = modeSubject.value()
        set(value) = modeSubject.publishUpdate(value)

    var selectedEntity: FieldConfigData
        get() = selectedEntitySubject.value()
        private set(value) = selectedEntitySubject.publishUpdate(value)

    fun isConnectingMode(): Boolean = editorMode == EditorMode.CONNECT
    fun isSelectMode(): Boolean = editorMode == EditorMode.SELECT
    fun isEditMode(): Boolean = editorMode == EditorMode.EDIT

    private fun createNewField(type: FieldType): FieldConfigData {
        return FieldConfigData(fieldType = type)
    }

    private fun selectFieldConnection(fieldConfigData: FieldConfigData): FieldConfigData {
        return when (selectedEntity) {
            NONE_FIELD_CONFIG -> {
                addConnection(fieldConfigData, selectedEntity)
                NONE_FIELD_CONFIG
            }
            else -> fieldConfigData
        }
    }

    private fun addConnection(field1: FieldConfigData, field2: FieldConfigData) {
        field1.connections.add(field1.gamePosition)
        field2.connections.add(field2.gamePosition)
        addEntitySubject.publishUpdate(AddEntity.AddConnection(field1, field2))
    }

    fun selectEntity(fieldConfigData: FieldConfigData) {
        selectedEntity = when (editorMode) {
            EditorMode.CONNECT -> {
                selectFieldConnection(fieldConfigData)
            }
            EditorMode.SELECT,
            EditorMode.EDIT -> {
                fieldConfigData
            }
        }
    }

    fun addEntity(type: FieldType) {
        val newField = createNewField(type)
        addEntitySubject.publishUpdate(AddEntity.AddField(newField))
        selectedEntity = newField
    }

    fun connectToField(clickedField: FieldConfigData): Boolean {
        editorMode = EditorMode.CONNECT
        selectedEntity = clickedField
        return true
    }

    fun updateFieldPosition(newPosition: PositionData) {
        selectedEntity.gamePosition.setPosition(newPosition)
    }

}