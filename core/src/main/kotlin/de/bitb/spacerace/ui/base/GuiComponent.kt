package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import de.bitb.spacerace.config.FONT_COLOR_BUTTON
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_WIDTH
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.grafik.TextureCollection

interface GuiComponent {

    //COMPONENTS

    fun createGroup(vararg actors: Actor): Group {
        val group = Group()

        group.width = GAME_LABEL_WIDTH + GAME_LABEL_PADDING * 2
        group.height = GAME_LABEL_HEIGHT * actors.size

        val background = Image(TextureCollection.guiBackground)
        background.width = group.width
        background.height = group.height
        group.addActor(background)
        var posY = 0f
        for (actor in actors) {
            actor.setPosition(GAME_LABEL_PADDING, posY)
            group.addActor(actor)
            posY += GAME_LABEL_HEIGHT
        }

        return group
    }

    fun createLabel(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, fontColor: Color = Color.BLACK): Label {
        val label = Label(name, TextureCollection.skin, "default")
        label.width = GAME_LABEL_WIDTH
        label.height = GAME_LABEL_HEIGHT
        label.setPosition(posX, posY)
        label.color = color
        setFont(label, fontColor = fontColor)
        return label
    }

    fun createCheckbox(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, fontColor: Color = Color.BLACK, fontSize: Float = GAME_SIZE_FONT_MEDIUM, listener: InputListener): CheckBox {
        val checkBox = CheckBox(name, TextureCollection.skin, "default")
        checkBox.width = GAME_LABEL_WIDTH
        checkBox.height = GAME_LABEL_HEIGHT
        checkBox.setPosition(posX, posY)
        checkBox.color = color
        checkBox.addListener(listener)
        setFont(checkBox, fontSize, fontColor)
        return checkBox
    }

    fun createButton(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, fontColor: Color = Color.BLACK, fontSize: Float = GAME_SIZE_FONT_SMALL, listener: InputListener): TextButton {
        val textButton = TextButton(name, TextureCollection.skin, "default")
        textButton.width = GAME_LABEL_WIDTH
        textButton.height = GAME_LABEL_HEIGHT
        textButton.setPosition(posX, posY)
        textButton.color = color
        textButton.addListener(listener)
        setFont(textButton.label, fontSize, fontColor)
        return textButton
    }

    fun createImageButton(imageIdle: Drawable, imageClick: Drawable = imageIdle, imageClicked: Drawable = imageIdle, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, listener: InputListener): ImageButton {
        val button = ImageButton(imageIdle, imageClick, imageClicked)
        button.width = GAME_LABEL_WIDTH
        button.height = GAME_LABEL_HEIGHT
        button.setPosition(posX, posY)
        button.color = color
        button.addListener(listener)
        return button
    }

    //ATTRIBUTES

    fun setFont(label: Label, fontSize: Float = GAME_SIZE_FONT_MEDIUM, fontColor: Color = Color.BLACK) {
        label.setFontScale(fontSize)

        val labelStyle = Label.LabelStyle(label.style)
        labelStyle.fontColor = fontColor
        labelStyle.font = label.style.font
        labelStyle.background = label.style.background

        label.style = labelStyle
    }

    fun setFont(textButton: TextButton, fontSize: Float = GAME_SIZE_FONT_MEDIUM, fontColor: Color = Color.WHITE) {
        textButton.label.setFontScale(fontSize)

        val labelStyle = TextButton.TextButtonStyle(textButton.style)
        labelStyle.fontColor = fontColor
        labelStyle.font = textButton.style.font

        textButton.style = labelStyle
    }

    fun setFont(checkBox: CheckBox, fontSize: Float = GAME_SIZE_FONT_MEDIUM, fontColor: Color = FONT_COLOR_BUTTON) {

        val checkboxStyle = CheckBox.CheckBoxStyle(checkBox.style)
        checkboxStyle.fontColor = fontColor
        checkboxStyle.font = checkBox.style.font

        checkboxStyle.checkboxOff.minHeight = checkBox.height - GAME_LABEL_PADDING / 2
        checkboxStyle.checkboxOff.minWidth = checkBox.height - GAME_LABEL_PADDING / 2
        checkboxStyle.checkboxOn.minHeight = checkBox.height - GAME_LABEL_PADDING / 2
        checkboxStyle.checkboxOn.minWidth = checkBox.height - GAME_LABEL_PADDING / 2

        checkBox.style = checkboxStyle
        checkBox.label.setFontScale(fontSize)

    }


    //LAYOUT

    fun <T : Actor> addPadding(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        cell.pad(padding)
    }

    fun <T : Actor> addPaddingTopBottom(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        addPaddingTop(cell, padding)
        addPaddingBottom(cell, padding)
    }

    fun <T : Actor> addPaddingLeftRight(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        addPaddingLeft(cell, padding)
        addPaddingRight(cell, padding)
    }

    fun <T : Actor> addPaddingTop(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        cell.padTop(padding)
    }

    fun <T : Actor> addPaddingBottom(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        cell.padBottom(padding)
    }

    fun <T : Actor> addPaddingLeft(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        cell.padLeft(padding)
    }

    fun <T : Actor> addPaddingRight(cell: Cell<T>, padding: Float = GAME_LABEL_PADDING / 2) {
        cell.padRight(padding)
    }
}