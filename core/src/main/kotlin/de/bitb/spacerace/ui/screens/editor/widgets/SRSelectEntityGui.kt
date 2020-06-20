package de.bitb.spacerace.ui.screens.editor.widgets

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import de.bitb.spacerace.ui.screens.editor.EditorMode
import javax.inject.Inject

class SRSelectEntityGui() : VisTable(), GuiBuilder {

    @Inject
    protected lateinit var editorGloc: EditorGloc

    private var modeLabel: VisLabel = createLabel(text = EditorMode.PLACE.name)

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        add(modeLabel)
        modeLabel.setAlignment(Align.center)

        setContent()
        pack()
    }

    private fun setContent() {
        FieldType.values().forEach { addEntityButton(it) }
    }

    private fun addEntityButton(fieldType: FieldType) {
        createSmallButtons(
                text = fieldType.name,
                listener = {
//                    editorBloc.
//                    if(editorBloc.editorMode == EditorMode.PLACE){
//   TODO das hier is die stellle zum weiter machen
//                    }
//                    editorBloc.editorMode = mode
//                    modeLabel.setText(mode.name)
                })
    }

    private fun createSmallButtons(text: String, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener,
                    imageUp = TexturePool.getButton(IMAGE_PATH_BUTTON_UP),
                    imageDown = TexturePool.getButton(IMAGE_PATH_BUTTON_DOWN)
            ).also { add(it) }
}