package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditorBloc
@Inject constructor(
        val modeSubject: EditorModeDispenser,
        val selectedEntitySubject: SelectedEntityDispenser
) {
    lateinit var loadedMap: MapData

    var editorMode: EditorMode
        get() = modeSubject.value()
        set(value) = modeSubject.publishUpdate(value)

    private var selectedEntity: FieldConfigData
        get() = selectedEntitySubject.value()
        set(value) = selectedEntitySubject.publishUpdate(value)

    fun isDragMode(): Boolean = editorMode == EditorMode.DRAG
    fun isSelectMode(): Boolean = editorMode == EditorMode.PLACE
    fun isEditMode(): Boolean = editorMode == EditorMode.EDIT

    fun selectEntity(fieldConfigData: FieldConfigData) {
        if (isSelectMode() || isEditMode()) {
            selectedEntity = fieldConfigData
        }
    }

    fun onLongClickField(clickedField: FieldConfigData): Boolean {
        editorMode = EditorMode.DRAG
        selectedEntity = clickedField
        return true
    }


}