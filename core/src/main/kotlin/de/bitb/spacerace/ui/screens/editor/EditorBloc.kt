package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import javax.inject.Inject
import javax.inject.Singleton

enum class EditorMode {
    SELECT, PLACE, EDIT, DRAG
}

@Singleton
class EditorBloc
@Inject constructor(

) {
    lateinit var loadedMap: MapData
    var editorMode: EditorMode = EditorMode.PLACE //TODO make on change listener -> als RX?
    private var selectedEntity: FieldConfigData? = null

    fun isDragMode(): Boolean = editorMode == EditorMode.DRAG
    fun isSelectMode(): Boolean = editorMode == EditorMode.PLACE
    fun isEditMode(): Boolean = editorMode == EditorMode.EDIT

    fun onLongClickField(fieldConfigDatas: List<FieldConfigData>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun selectEntity(fieldConfigData: FieldConfigData) {
        if (isSelectMode() || isEditMode()) {
            selectedEntity = fieldConfigData
        }
    }

}