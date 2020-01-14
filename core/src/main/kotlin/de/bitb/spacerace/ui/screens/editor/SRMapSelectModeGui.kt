package de.bitb.spacerace.ui.screens.editor

import com.kotcrab.vis.ui.widget.VisTextField
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import javax.inject.Inject

class SRMapSelectModeGui : SRGuiGrid() {

    @Inject
    protected lateinit var editorBloc: EditorBloc

    private var modeLabel: VisTextField

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        modeLabel = VisTextField()
        setContent()
        setDimensions()
        pack()
    }

    private fun setDimensions() {
        setItemSize(GAME_BUTTON_WIDTH_DEFAULT * .7f, GAME_BUTTON_HEIGHT_DEFAULT * .7f)
        setGuiBorder(
                columns = 1f,
                rows = 4f,
                alignHoriz = SRAlign.LEFT,
                alignVert = SRAlign.BOTTOM)
    }

    private fun setContent() {
        setModeBtn(EditorMode.SELECT)
        setModeBtn(EditorMode.DRAG)
        setModeBtn(EditorMode.EDIT)
    }

    private fun setModeBtn(mode: EditorMode) {
        createSmallButtons(
                text = mode.name,
                listener = {
                    editorBloc.editorMode = mode
                    modeLabel.text = mode.name
                })
    }

    private fun createSmallButtons(text: String, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener,
                    imageUp = TexturePool.getButton(IMAGE_PATH_BUTTON_UP),
                    imageDown = TexturePool.getButton(IMAGE_PATH_BUTTON_DOWN)
            )
                    .also {
                        addActor(it)
                    }
}