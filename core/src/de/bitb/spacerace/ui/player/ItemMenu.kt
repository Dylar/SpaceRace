package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.Strings
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.base.GuiComponent

class ItemMenu(val space: BaseSpace, guiComponent: GuiComponent = object : GuiComponent {}) : Table(TextureCollection.skin), GuiComponent by guiComponent {

    companion object {
        private const val BASE_WIDTH = 4
    }

    var isOpen: Boolean = false

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val player = space.currentPlayer
        val items = player.items
        var size = player.items.size
        size = if (size < BASE_WIDTH) BASE_WIDTH else size

        addTitle(size)
        addItems(size, items)
        addButtons(size)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = (SCREEN_WIDTH - (SCREEN_WIDTH / 2) - width / 2)
        y = (SCREEN_HEIGHT - (SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addTitle(size: Int) {
        val cell = add("Items")
        setFont(cell.actor)
        cell.colspan(size)
    }

    private fun addItems(size: Int, items: ArrayList<Item>) {
        Logger.println("START ITEM---------------")
        row()

//        val container = Table(TextureCollection.skin)
//        val cell = add(container)
//        cell.colspan(items.size)

        var i = 0
        for (item in items) {
            i++
            Logger.println("ADD ITEM $i")
            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    isOpen = false
                    return true
                }
            })
            val v = add(item)
//            val v = container.add(item)
//            v.colspan(1)
        }

        Logger.println("END ITEM---------------")
    }

    private fun addButtons(size: Int) {
        row()
        val cancelBtn = createButton(name = Strings.GameGuiStrings.GAME_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                isOpen = false
                return true
            }
        })
        val cellBtn = add(cancelBtn)
        cellBtn.colspan(size)
        setFont(cellBtn.actor)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (!isOpen) {
            remove()
        }
    }


}