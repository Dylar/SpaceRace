package de.bitb.spacerace.ui.screens.editor.widgets

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisLabel
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import de.bitb.spacerace.ui.screens.editor.EditorMode
import de.bitb.spacerace.usecase.DisposableHandler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SRMapSelectModeGui : SRGuiGrid(), DisposableHandler {

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    protected lateinit var editorGloc: EditorGloc

    private var modeLabel: VisLabel = createLabel(text = EditorMode.SELECT.name)

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        addActor(modeLabel)
        modeLabel.setAlignment(Align.center)

        setContent()
        setDimensions()
        pack()

        editorGloc.observeEditorModeUseCase.observeStream {
            modeLabel.setText(it.name)
        }.addDisposable()
    }

    override fun remove(): Boolean {
        disposeDisposables()
        return super.remove()
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
        EditorMode.values().forEach { setModeBtn(it) }
    }

    private fun setModeBtn(mode: EditorMode) {
        createSmallButtons(
                text = mode.name,
                listener = {
                    editorGloc.changModeClicked(mode)
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