package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
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

        setWindowConfig()
        setContent()
        centerWindow()
        center()
        pack()
        fadeIn()
    }

    protected open fun inject() {
        MainGame.appComponent.inject(this)
    }

    private fun setWindowConfig() {
        pad(70f, 10f, 10f, 10f)
        titleLabel.setText(getTitle())
        titleLabel.setAlignment(Align.center)
        style = WindowStyle().also {
            it.titleFont = TexturePool.bitmapFont
            it.titleFontColor = Color.TEAL
            it.background = TexturePool.getBackground(IMAGE_PATH_GUI_BACKGROUND)
        }
    }

    abstract fun getTitle(): String
    abstract fun setContent()

}