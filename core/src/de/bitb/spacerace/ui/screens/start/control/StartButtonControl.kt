package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_LANGUAGE
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_PLAYER
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_START
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.ChangeLanguageCommand
import de.bitb.spacerace.events.commands.start.ChangePlayerAmountCommand
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage

class StartButtonControl(val space: GameController, val guiStage: StartGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    private val inputHandler: InputHandler = guiStage.inputHandler

    private var languageBtn: TextButton
    private var playerBtn: TextButton
    private var startBtn: TextButton

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        startBtn = createButton(name = START_BUTTON_START, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(StartGameCommand())
                return true
            }
        })

        playerBtn = createButton(name = START_BUTTON_PLAYER, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangePlayerAmountCommand())
                return true
            }
        })

        languageBtn = createButton(name = START_BUTTON_LANGUAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangeLanguageCommand())
                return true
            }
        })

        setFont(addCell(startBtn).actor)
        row()
        setFont(addCell(playerBtn).actor)
        row()
        setFont(addCell(languageBtn).actor)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = SCREEN_WIDTH / 2 - width / 2
        y = SCREEN_HEIGHT / 1.5f - height / 2
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, singlePadding / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(event: T) {
        if (event is ChangeLanguageCommand) {
            updateButtonText()
        } else if (event is ChangePlayerAmountCommand) {
            updatePlayerButtonText()
        }
    }

    private fun updateButtonText() {
        updateStartButtonText()
        updatePlayerButtonText()
        updateLanguageButtonText()
    }

    private fun updateStartButtonText() {
        startBtn.label.setText(START_BUTTON_START)
    }

    private fun updatePlayerButtonText() {
        playerBtn.label.setText(START_BUTTON_PLAYER)
    }

    private fun updateLanguageButtonText() {
        languageBtn.label.setText(START_BUTTON_LANGUAGE)
    }

}