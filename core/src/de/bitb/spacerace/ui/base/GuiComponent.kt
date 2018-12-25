package de.bitb.spacerace.ui.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.slotHeight
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.slotWidth
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_BIG
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.core.TextureCollection

interface GuiComponent {

    //COMPONENTS

    fun createGroup(vararg actors: Actor): Group {
        val group = Group()

        group.width = slotWidth + singlePadding * 2
        group.height = slotHeight * actors.size

        val background = Image(TextureCollection.guiBackground)
        background.width = group.width
        background.height = group.height
        group.addActor(background)
        var posY = 0f
        for (actor in actors) {
            actor.setPosition(singlePadding, posY)
            group.addActor(actor)
            posY += slotHeight
        }

        return group
    }

    fun createLabel(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.BLACK): Label {
        val label = Label(name, TextureCollection.skin, "default")
        label.width = slotWidth
        label.height = slotHeight
        label.setPosition(posX, posY)
        label.color = color
        setFont(label, fontColor = colorText)
        return label
    }

    fun createCheckbox(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.BLACK, fontSize: Float = GAME_SIZE_FONT_MEDIUM, listener: InputListener): CheckBox {
        val checkBox = CheckBox(name, TextureCollection.skin, "default")
        checkBox.width = slotWidth
        checkBox.height = slotHeight
        checkBox.setPosition(posX, posY)
        checkBox.color = color
        checkBox.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                Gdx.graphics.isContinuousRendering = checkBox.isChecked()
                listener.touchDown(event, x, y, pointer, button)
                return true
            }

            fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                Gdx.graphics.isContinuousRendering = checkBox.isChecked()
            }
        })
        setFont(checkBox, fontSize, colorText)
        return checkBox
    }

    fun createButton(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.BLACK, fontSize: Float = GAME_SIZE_FONT_SMALL, listener: InputListener): TextButton {
        val textButton = TextButton(name, TextureCollection.skin, "default")
        textButton.width = slotWidth
        textButton.height = slotHeight
        textButton.setPosition(posX, posY)
        textButton.color = color
        textButton.addListener(listener)
        setFont(textButton.label, fontSize, colorText)
        return textButton
    }

    fun createImageButton(imageIdle: Drawable, imageClick: Drawable = imageIdle, imageClicked: Drawable = imageIdle, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, listener: InputListener): ImageButton {
        val button = ImageButton(imageIdle, imageClick, imageClicked)
        button.width = slotWidth
        button.height = slotHeight
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

    fun setFont(checkBox: CheckBox, fontSize: Float = GAME_SIZE_FONT_MEDIUM, fontColor: Color = Color.WHITE) {

        val labelStyle = CheckBox.CheckBoxStyle(checkBox.style)
        labelStyle.fontColor = fontColor
        labelStyle.font = checkBox.style.font

        checkBox.style = labelStyle
        checkBox.label.setFontScale(fontSize)

    }


    //LAYOUT

    fun <T : Actor> addPadding(cell: Cell<T>, padding: Float = singlePadding / 2) {
        cell.pad(padding)
    }

    fun <T : Actor> addPaddingTopBottom(cell: Cell<T>, padding: Float = singlePadding / 2) {
        addPaddingTop(cell, padding)
        addPaddingBottom(cell, padding)
    }

    fun <T : Actor> addPaddingLeftRight(cell: Cell<T>, padding: Float = singlePadding / 2) {
        addPaddingLeft(cell, padding)
        addPaddingRight(cell, padding)
    }

    fun <T : Actor> addPaddingTop(cell: Cell<T>, padding: Float = singlePadding / 2) {
        cell.padTop(padding)
    }

    fun <T : Actor> addPaddingBottom(cell: Cell<T>, padding: Float = singlePadding / 2) {
        cell.padBottom(padding)
    }

    fun <T : Actor> addPaddingLeft(cell: Cell<T>, padding: Float = singlePadding / 2) {
        cell.padLeft(padding)
    }

    fun <T : Actor> addPaddingRight(cell: Cell<T>, padding: Float = singlePadding / 2) {
        cell.padRight(padding)
    }
}