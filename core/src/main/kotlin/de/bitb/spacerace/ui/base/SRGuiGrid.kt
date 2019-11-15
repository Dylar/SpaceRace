package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_GUI_PADDING_SPACE
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool
import javax.inject.Inject

abstract class SRGuiGrid : GridGroup(), GuiComponent {

    @Inject
    lateinit var graphicController: GraphicController

    init {
//        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT
    }

    protected fun setBackgroundByPath(backgroundPath: String) {
//        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
    }

    protected fun setGuiBorder(
            columns: Float,
            rows: Float,
            guiPosX: Float = 0f,
            guiPosY: Float = 0f,
            alignHoriz: SRAlign = SRAlign.NONE,
            alignVert: SRAlign = SRAlign.NONE
    ) {
        width = itemWidth * columns + spacing * 2 * columns
        height = itemHeight * rows + spacing * 2 * rows
        val translateX = when (alignHoriz) {
            SRAlign.LEFT -> GAME_GUI_PADDING_SPACE
            SRAlign.RIGHT -> -GAME_GUI_PADDING_SPACE - width
            else -> 0f
        }
        val translateY = when (alignVert) {
            SRAlign.TOP -> -GAME_GUI_PADDING_SPACE - height
            SRAlign.BOTTOM -> GAME_GUI_PADDING_SPACE
            else -> 0f
        }
        x = guiPosX + translateX
        y = guiPosY + translateY
    }

    protected fun addEmptySlot() {
        val emptySlot = VisLabel()
        emptySlot.width = itemWidth
        emptySlot.height = itemHeight
        addActor(emptySlot)
    }

    protected fun createTextButtons(
            text: String,
            imageUp: String = IMAGE_PATH_BUTTON_UP,
            imageDown: String = IMAGE_PATH_BUTTON_DOWN,
            width: Float = GAME_BUTTON_WIDTH_DEFAULT,
            height: Float = GAME_BUTTON_HEIGHT_DEFAULT,
            listener: () -> Unit
    ): VisTextButton {

        val style = VisTextButton.VisTextButtonStyle()
        style.up = TexturePool.getNinePatch(imageUp)
        style.down = TexturePool.getNinePatch(imageDown)
        style.font = TexturePool.bitmapFont
        style.downFontColor = Color.RED //TODO in settings
        style.fontColor = Color.TEAL
        style.disabledFontColor = Color.DARK_GRAY

        val textButton = VisTextButton(text, style)
        textButton.addListener(createListener(listener))
        textButton.width = width
        textButton.height = height
        return textButton
    }

    private fun createListener(action: () -> Unit) =
            object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    action()
                }
            }

}

enum class SRAlign {
    NONE, LEFT, RIGHT, TOP, BOTTOM
}
