package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_LANGUAGE
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_START
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.ChangeLanguageCommand
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.events.commands.start.ChangeWinAmountCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage

class StartButtonControl(guiStage: StartGuiStage) : BaseGuiControl(guiStage) {

    private var startBtn: TextButton
    private var winLabel: Label
    private var languageBtn: TextButton

    init {

        startBtn = createButton(name = START_BUTTON_START, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(StartGameCommand())
                return true
            }
        })

        val lessWinBtn = createButton(name = "-", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangeWinAmountCommand(-1))
                return true
            }
        })

        winLabel = createLabel(WIN_AMOUNT.toString())

        val moreWinBtn = createButton(name = "+", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangeWinAmountCommand(1))
                return true
            }
        })

        languageBtn = createButton(name = START_BUTTON_LANGUAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangeLanguageCommand())
                return true
            }
        })

        var cell = addCell(startBtn)
        cell.colspan(3)
        setFont(cell.actor)
        row()

        val cell1 = addCell(createLabel("Wins"))
        cell1.colspan(3)
        setFont(cell1.actor)
        row()

        setFont(addCell(lessWinBtn).actor)
        setFont(add(winLabel).actor)
        setFont(addCell(moreWinBtn).actor)
        row()

        cell = addCell(languageBtn)
        cell.colspan(3)
        setFont(cell.actor)

        pack()
    }


    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        if (event is ChangeLanguageCommand || event is ChangeWinAmountCommand) {
            updateButtonText()
        }
    }

    private fun updateButtonText() {
        updateStartButtonText()
        updateWinLabelText()
        updateLanguageButtonText()
    }

    private fun updateWinLabelText() {
        winLabel.setText(WIN_AMOUNT.toString())
    }

    private fun updateStartButtonText() {
        startBtn.label.setText(START_BUTTON_START)
    }

    private fun updateLanguageButtonText() {
        languageBtn.label.setText(START_BUTTON_LANGUAGE)
    }

}