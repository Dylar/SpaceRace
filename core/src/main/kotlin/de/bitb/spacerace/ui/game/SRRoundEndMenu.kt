package de.bitb.spacerace.ui.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.events.commands.phases.StartNextRoundCommand
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.player.PlayerAnimation
import de.bitb.spacerace.grafik.model.player.PlayerGraphics
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRRoundEndMenu
    : VisWindow("Round end"),
        GuiBackstack by GuiBackstackHandler,
        GuiBuilder {

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        debug = DEBUG_LAYOUT
        MainGame.appComponent.inject(this)

        pad(70f, 10f, 10f, 10f)
        titleLabel.setAlignment(Align.center)
        style = WindowStyle().also {
            it.titleFont = TexturePool.bitmapFont
            it.titleFontColor = Color.TEAL
            it.background = TexturePool.getBackground(IMAGE_PATH_GUI_BACKGROUND)
        }
        setContent()
        setDimensions()
        pack()
        center()
    }

    private fun setDimensions() {
        centerWindow()
//        alignGui(
//                guiPosX = SCREEN_WIDTH * 0.85f,
//                guiPosY = SCREEN_HEIGHT * 0.8f,
//                guiWidth = width,
//                guiHeight = height,
//                alignHoriz = SRAlign.RIGHT,
//                alignVert = SRAlign.TOP)
    }

    private fun setContent() {
        graphicController.playerGraphics
                .forEach {
                    val animation = it.playerImage.animation as PlayerAnimation
                    val imageUp = animation.getDefaultImage()?.texture ?: error("NO ANIMATION")
                    val imageDown = animation.getLastImage()?.texture ?: error("NO ANIMATION")
                    val createBtn = createTextButtons(
                            "",
                            imageUp = TexturePool.getNinePatch(imageUp).tint(it.playerColor.color),
                            imageDown = TexturePool.getNinePatch(imageDown).tint(it.playerColor.color)
                    ) { openPlayerDetails(it) }
                    add(createBtn).expand()
                }
        row().pad(20f).colspan(graphicController.playerGraphics.size + 2)
        addButton("Continue") {
            clearBackstack()
            EventBus.getDefault().post(StartNextRoundCommand.get())
        }
    }

    private fun openPlayerDetails(player: PlayerGraphics) {
        EventBus.getDefault().post(GuiNavi.PlayerEndDetailsMenu(player.playerColor))
    }

    private fun addButton(text: String, listener: () -> Unit) {
        createTextButtons(
                text = text,
                listener = listener)
                .also {
                    add(it).expandX()
                }
    }
}