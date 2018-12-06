package de.bitb.spacerace.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.TextureCollection

interface GuiComponent {

    fun createGroup(vararg actors: Actor): Group {
        val group = Group()

        group.width = BaseGuiStage.slotWidth + BaseGuiStage.singlePadding * 2
        group.height = BaseGuiStage.slotHeight * actors.size

        val background = Image(TextureCollection.guiBackground)
        background.width = group.width
        background.height = group.height
        group.addActor(background)
        var posY = 0f
        for (actor in actors) {
            actor.setPosition(BaseGuiStage.singlePadding, posY)
            group.addActor(actor)
            posY += BaseGuiStage.slotHeight
        }

        return group
    }

    fun createLabel(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED): Label {
        val label = Label(name, TextureCollection.skin, "default")
        label.width = BaseGuiStage.slotWidth
        label.height = BaseGuiStage.slotHeight
        label.setPosition(posX, posY)
        label.color = color
        label.style.fontColor = colorText
        label.setFontScale(BaseGuiStage.fontSize)
        return label
    }

    fun createButton(name: String = "-", posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED, listener: InputListener): TextButton {
        val button = TextButton(name, TextureCollection.skin, "default")
        button.width = BaseGuiStage.slotWidth
        button.height = BaseGuiStage.slotHeight
        button.setPosition(posX, posY)
        button.color = color
        button.style.fontColor = colorText
        button.addListener(listener)
        button.label.setFontScale(BaseGuiStage.fontSize)
        return button
    }

    fun createImageButton(imageIdle: Drawable, imageClick: Drawable = imageIdle, imageClicked: Drawable = imageIdle, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, listener: InputListener): ImageButton {
        val button = ImageButton(imageIdle, imageClick, imageClicked)
        button.width = BaseGuiStage.slotWidth
        button.height = BaseGuiStage.slotHeight
        button.setPosition(posX, posY)
        button.color = color
        button.addListener(listener)
        return button
    }
}