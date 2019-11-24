package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.FONT_COLOR_BUTTON
import de.bitb.spacerace.config.FONT_COLOR_TITLE
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool

interface GuiBuilder {

    fun createListener(action: () -> Unit) =
            object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    action()
                }
            }

//    fun createImageButtons(
//            text: String,
//            imageUp: NinePatchDrawable = TexturePool.getButton(IMAGE_PATH_BUTTON_UP),
//            imageDown: NinePatchDrawable = TexturePool.getButton(IMAGE_PATH_BUTTON_DOWN),
//            width: Float = GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT,
//            height: Float = GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT,
//            listener: () -> Unit
//    ): VisTextButton {
//
//        val style = VisTextButton.VisTextButtonStyle()
//        style.up = imageUp
//        style.down = imageDown
//        style.font = TexturePool.bitmapFont
//        style.downFontColor = Color.RED //TODO in settings
//        style.fontColor = Color.TEAL
//        style.disabledFontColor = Color.DARK_GRAY
//
//        val textButton = VisTextButton(text, style)
//        textButton.addListener(createListener(listener))
//        textButton.width = width
//        textButton.height = height
//        return textButton
//    }

    fun createTextButtons(
            text: String,
            imageUp: Drawable = TexturePool.getButton(IMAGE_PATH_BUTTON_UP),
            imageDown: Drawable = TexturePool.getButton(IMAGE_PATH_BUTTON_DOWN),
            width: Float = GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT,
            height: Float = GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT,
            listener: () -> Unit
    ): VisTextButton {

        val style = VisTextButton.VisTextButtonStyle()
        style.up = imageUp
        style.down = imageDown
        style.font = TexturePool.bitmapFont
        style.downFontColor = Color.RED //TODO in settings
        style.fontColor = FONT_COLOR_BUTTON
        style.disabledFontColor = Color.DARK_GRAY

        val textButton = VisTextButton(text, style)
        textButton.addListener(createListener(listener))
        textButton.width = width
        textButton.height = height
        return textButton
    }

    fun createLabel(
            text: String = "",
            background: String? = null,
            fontColor: Color = FONT_COLOR_TITLE,
            width: Float = GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT,
            height: Float = GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT,
            listener: () -> Unit = {}
    ): VisLabel {

        val style = Label.LabelStyle()
        background?.also { style.background = TexturePool.getBackground(it) }
        style.font = TexturePool.bitmapFont
        style.fontColor = fontColor //TODO in settings -> or?

        val label = VisLabel(text, style)
        label.addListener(createListener(listener))
//        label.width = width
//        label.height = height
        label.setSize(width, height)
        return label
    }

    fun VisTable.setBackgroundByPath(backgroundPath: String) {
        background = TexturePool.getBackground(backgroundPath)
//        background = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal(backgroundPath))))
    }

    fun VisTable.scaleTable(scaleWidth: Float = 1.0f, scaleHeight: Float = 1.0f) {
        width *= scaleWidth
        height *= scaleHeight
    }

    fun Actor.alignGui(
            guiPosX: Float = 0f,
            guiPosY: Float = 0f,
            guiWidth: Float = 0f,
            guiHeight: Float = 0f,
            alignHoriz: SRAlign = SRAlign.NONE,
            alignVert: SRAlign = SRAlign.NONE
    ) {
        val translateX = when (alignHoriz) {
            SRAlign.LEFT -> GameGuiDimensions.GAME_GUI_PADDING_SPACE
            SRAlign.RIGHT -> -GameGuiDimensions.GAME_GUI_PADDING_SPACE - guiWidth
            else -> 0f
        }
        val translateY = when (alignVert) {
            SRAlign.TOP -> -GameGuiDimensions.GAME_GUI_PADDING_SPACE - guiHeight
            SRAlign.BOTTOM -> GameGuiDimensions.GAME_GUI_PADDING_SPACE
            else -> 0f
        }
        x = guiPosX + translateX
        y = guiPosY + translateY
    }
}