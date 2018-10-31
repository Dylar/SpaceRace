package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.ui.GuiComponent

open class BaseGuiStage(viewport: Viewport = ScreenViewport()) : BaseStage(viewport), GuiComponent {
    companion object {
        val lineWidth = 30

        val slotHeight = Gdx.graphics.height / 15f
        val slotWidth = Gdx.graphics.width / 6f
        val singlePadding = slotWidth * 0.2f
        val guiHeight = slotHeight * 2
        val guiWidth = slotWidth * 2 + singlePadding * 2
        val guiPosX = Gdx.graphics.width - guiWidth
    }
//
//    fun createGroup(vararg actors: Actor): Group {
//        val group = Group()
//
//        group.width = slotWidth + singlePadding * 2
//        group.height = slotHeight * actors.size
//
//        val background = Image(TextureCollection.guiBackground)
//        background.width = group.width
//        background.height = group.height
//        group.addActor(background)
//        var posY = 0f
//        for (actor in actors) {
//            actor.setPosition(singlePadding, posY)
//            group.addActor(actor)
//            posY += slotHeight
//        }
//
//        return group
//    }

//    protected fun createTable() :Table{
//
//    }

//    override fun createLabel(skin: Skin = TextureCollection.skin, name: String = "-", width: Float = slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED): Label {
//        val label = Label(name, skin, "default")
//        label.width = width
//        label.setPosition(posX, posY)
//        label.color = color
//        label.style.fontColor = colorText
//        return label
//    }
//
//    protected fun createButton(skin: Skin = TextureCollection.skin, name: String = "-", width: Float = slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED, listener: InputListener): TextButton {
//        val button = TextButton(name, skin, "default")
//        button.width = width
//        button.setPosition(posX, posY)
//        button.color = color
//        button.style.fontColor = colorText
//        button.addListener(listener)
//        return button
//    }
//
//    protected fun createImageButton(imageIdle: Drawable, imageClick: Drawable = imageIdle, imageClicked: Drawable = imageIdle, width: Float = slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, listener: InputListener): ImageButton {
//        val button = ImageButton(imageIdle, imageClick, imageClicked)
//        button.width = width
//        button.setPosition(posX, posY)
//        button.color = color
//        button.addListener(listener)
//        return button
//    }
}