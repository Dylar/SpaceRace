package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import javax.inject.Inject
import javax.inject.Singleton

enum class EditorMode {
    SELECT, EDIT, DRAG
}

@Singleton
class EditorBloc
@Inject constructor(

) {
    var editorMode: EditorMode = EditorMode.SELECT
    private var selectedEntity: FieldConfigData? = null

    fun isDragMode(): Boolean = editorMode == EditorMode.DRAG
    fun isSelectMode(): Boolean = editorMode == EditorMode.SELECT
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