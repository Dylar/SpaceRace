package de.bitb.spacerace.ui.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.FONT_COLOR_BUTTON
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_WINDOW_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_WINDOW_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.grafik.IMAGE_PATH_WINDOW_BACKGROUND
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import javax.inject.Inject

abstract class SRWindowGui : VisWindow(""),
        GuiBackstack by GuiBackstackHandler,
        GuiBuilder {

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        debug = DEBUG_LAYOUT
    }

    protected fun initWindow() {
        inject()

        Gdx.app.postRunnable {
            setWindowConfig()
            setContent()
            centerWindow()
            center()
            pack()
            fadeIn()
        }
    }

    protected open fun inject() {
        MainGame.appComponent.inject(this)
    }

    private fun setWindowConfig() {
        pad(110f, 10f, 10f, 10f)

        titleLabel.setText(getTitle())
        titleLabel.setAlignment(Align.center)
        style = WindowStyle().also {
            it.titleFont = TexturePool.bitmapFont
            it.titleFontColor = FONT_COLOR_BUTTON
            it.background = TexturePool.getBackground(IMAGE_PATH_WINDOW_BACKGROUND, GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT)
//                    .apply {
//                        minWidth = Dimensions.GameGuiDimensions.GAME_WINDOW_WIDTH
//                        minHeight = Dimensions.GameGuiDimensions.GAME_WINDOW_HEIGHT
//                    }
        }
    }

    abstract fun getTitle(): String
    abstract fun setContent()

}