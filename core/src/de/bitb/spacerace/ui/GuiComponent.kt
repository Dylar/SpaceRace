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

    fun createLabel(skin: Skin = TextureCollection.skin, name: String = "-", width: Float = BaseGuiStage.slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED): Label {
        val label = Label(name, skin, "default")
        label.width = width
        label.setPosition(posX, posY)
        label.color = color
        label.style.fontColor = colorText
        return label
    }

    fun createButton(skin: Skin = TextureCollection.skin, name: String = "-", width: Float = BaseGuiStage.slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED, listener: InputListener): TextButton {
        val button = TextButton(name, skin, "default")
        button.width = width
        button.setPosition(posX, posY)
        button.color = color
        button.style.fontColor = colorText
        button.addListener(listener)
        return button
    }

    fun createImageButton(imageIdle: Drawable, imageClick: Drawable = imageIdle, imageClicked: Drawable = imageIdle, width: Float = BaseGuiStage.slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, listener: InputListener): ImageButton {
        val button = ImageButton(imageIdle, imageClick, imageClicked)
        button.width = width
        button.setPosition(posX, posY)
        button.color = color
        button.addListener(listener)
        return button
    }
}