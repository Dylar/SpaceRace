package de.bitb.spacerace.ui.player

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.base.BaseMenu


class ItemMenuOld(space: BaseSpace, guiStage: BaseGuiStage) : BaseMenu<Item>(space, guiStage) {

    override fun getTitle(): String{
        return "Items"
    }

    override fun populateButtons(buttonTable: Table) {
        val buttonA = TextButton("USE", skin)
        val buttonB = createButton(name = "CANCEL", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                toggle()
                return true
            }
        })
        buttonTable.row().fillX().expandX()
        buttonTable.add(buttonA).width(windowWidth / 4.0f)
        buttonTable.add(buttonB).width(windowWidth / 4.0f)
    }

    override fun onVisible() {
        populateItems(space.currentPlayer.items)
    }

    override fun populateItem(item: Item) {

    }

}