package de.bitb.spacerace.ui.screens.editor.widgets

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import javax.inject.Inject

class SRSelectEntityGui : SRGuiGrid(), GuiBuilder {

    @Inject
    protected lateinit var editorGloc: EditorGloc

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        setContent()
        setDimensions()
        pack()
    }

    private fun setDimensions() {
        setItemSize(
                Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT,
                Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT)
        setGuiBorder(
                columns = 2f,
                rows = 2f,
                guiPosX = Dimensions.SCREEN_WIDTH,
                alignHoriz = SRAlign.RIGHT,
                alignVert = SRAlign.BOTTOM
        )
    }

    private fun setContent() {
        FieldType.values().forEach { addEntityButton(it) }
    }

    private fun addEntityButton(fieldType: FieldType) {
        createTextButtons(
                text = "",
                imageUp = TexturePool.getDrawable(fieldType.texture),
                imageDown = TexturePool.getDrawable(fieldType.texture).tint(Color.GRAY),
                listener = {
                    editorGloc.addEntityClicked(fieldType)
                }
        ).also { addActor(it) }
    }
}