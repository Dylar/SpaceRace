package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.SelectPlayerCommand
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage


class PlayerSelectionControl(val gameController: GameController, val guiStage: StartGuiStage, val inputHandler: InputHandler = guiStage.inputHandler) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        for (value in PlayerColor.values()) {
            if (value != PlayerColor.NONE) {
                val checkBox = addCheckbox(value)
                if (value == PlayerColor.RED || value == PlayerColor.TEAL) {
                    checkBox.isChecked = true
                    inputHandler.handleCommand(SelectPlayerCommand(value))
                }
            }
        }

        pack()

        setPosition()
    }

    private fun addCheckbox(color: PlayerColor): CheckBox {
        val checkBox = createCheckbox(name = color.name, fontSize = GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(SelectPlayerCommand(color))
                return true
            }
        })

        addCell(checkBox)
        val box = checkBox.cells.get(0)
        addPaddingRight(box)

        row()
        return checkBox
    }

    private fun setPosition() {
        x = SCREEN_WIDTH / 4 - width + width / 4
        y = SCREEN_HEIGHT / 2f - height / 2
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 5)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {

    }

}