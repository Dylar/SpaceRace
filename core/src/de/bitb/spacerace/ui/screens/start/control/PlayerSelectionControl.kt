package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_BIG
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_TINY
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


class PlayerSelectionControl(val space: GameController, val guiStage: StartGuiStage, val inputHandler: InputHandler = guiStage.inputHandler) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        for (value in PlayerColor.values()) {
            if (value != PlayerColor.NONE) {
                addCheckbox(value)
            }
        }

        pack()

        setPosition()
    }

    private fun addCheckbox(color: PlayerColor) {
        val checkBox = createCheckbox(name = color.name, fontSize = GAME_SIZE_FONT_TINY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(SelectPlayerCommand(color))
                return true
            }
        })

        addCell(checkBox).actor
        val box = checkBox.cells.get(0).size(width, height)
        addPaddingRight(box)
        row()
    }

    private fun setPosition() {
        x = SCREEN_WIDTH / 4 - width + width / 4
        y = SCREEN_HEIGHT / 2f - height / 2
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, singlePadding / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
//        if (event is ChangeLanguageCommand) {
//            updateButtonText()
//        } else if (event is ChangePlayerAmountCommand) {
//            updatePlayerButtonText()
//        }
    }
//
//    private fun updateButtonText() {
//        updateStartButtonText()
//        updatePlayerButtonText()
//        updateLanguageButtonText()
//    }
//
//    private fun updateStartButtonText() {
//        startBtn.label.setText(START_BUTTON_START)
//    }
//
//    private fun updatePlayerButtonText() {
//        playerBtn.label.setText(START_BUTTON_PLAYER)
//    }
//
//    private fun updateLanguageButtonText() {
//        languageBtn.label.setText(START_BUTTON_LANGUAGE)
//    }

}