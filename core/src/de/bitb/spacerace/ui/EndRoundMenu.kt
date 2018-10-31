package de.bitb.spacerace.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.model.player.Ship
import de.bitb.spacerace.model.space.BaseSpace

class EndRoundMenu(space: BaseSpace, guiStage: BaseGuiStage) : BaseMenu<Ship>(space, guiStage) {
    override fun getTitle(): String {
        return "Last turn"
    }

    override fun createButtons(buttonTable: Table) {
        val buttonA = createButton(name = "Go on", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                toggle()
                space.nextPhase()
                return true
            }
        })
        buttonTable.row().fillX().expandX()
        buttonTable.add(buttonA).width(windowWidth / 4.0f)
    }

    override fun populateItem(item: Ship) {

    }

    override fun onVisible() {
        populateItems(space.ships)
    }
}