package de.bitb.spacerace.ui.game

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.BaseMenu

class EndRoundMenu(space: BaseSpace, guiStage: BaseGuiStage) : BaseMenu<Player>(space, guiStage) {
    override fun getTitle(): String {
        return "Last turn"
    }

    override fun populateButtons(buttonTable: Table) {
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

    override fun populateItem(item: Player) {
        (stage as GameGuiStage).openRoundDetails(item)
    }

    override fun onVisible() {
        populateItems(space.players)
    }
}