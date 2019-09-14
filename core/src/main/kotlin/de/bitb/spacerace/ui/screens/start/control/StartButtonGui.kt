package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_LANGUAGE
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_START
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.start.*
import de.bitb.spacerace.ui.screens.start.StartGuiStage
import de.bitb.spacerace.usecase.ui.ObserveCommandUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class StartButtonGui(
        guiStage: StartGuiStage
) : BaseGuiControl(guiStage) {

    @Inject
    lateinit var observeCommandUsecase: ObserveCommandUsecase

    private lateinit var startBtn: TextButton
    private lateinit var winLabel: Label
    private lateinit var languageBtn: TextButton
    private lateinit var diceLabel: Label
    private lateinit var debugBtn: TextButton
    private lateinit var loadBtn: TextButton

    private val maxSpan = 7

    init {
        MainGame.appComponent.inject(this)
        initObserver()

        addStartButton()
        addWinButtons()
        addDiceButtons()
        addLanguageButtons()
        addDebugButton()
        addLoadButton()
        pack()
    }

    private fun initObserver() {
        observeCommandUsecase.observeStream { event ->
            when (event) {
                is ChangeLanguageCommand -> updateButtonText()
                is ChangeWinAmountCommand -> updateWinLabelText()
                is ChangeDiceAmountCommand -> updateDiceLabelText()
            }
        }
    }

    override fun setPosition(x: Float, y: Float) {
        super.setPosition(x, y)
        updateDiceLabelText()
        updateWinLabelText()
    }

    private fun addStartButton() {
        startBtn = createButton(name = START_BUTTON_START, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(LoadGameCommand())
                return true
            }
        })
        val cell = addCell(startBtn)
        setFont(cell.actor)
        row()
    }

    private fun addWinButtons() {
        val lessWinBtn = createButton(name = "-", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(ChangeWinAmountCommand(-1))
                return true
            }
        })

        winLabel = createLabel("Wins: 100")

        val moreWinBtn = createButton(name = "+", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(ChangeWinAmountCommand(1))
                return true
            }
        })

        addAmountPanel(lessWinBtn, winLabel, moreWinBtn)

    }

    private fun addAmountPanel(lessBtn: TextButton, label: Label, moreBtn: TextButton) {
        setFont(addCell(lessBtn, 1).actor)
        val cell = add(label)
        cell.colspan(maxSpan - 3)
        setFont(cell.actor)
        setFont(addCell(moreBtn, 1).actor)
        row()
    }

    private fun addDiceButtons() {
        val lessDiceBtn = createButton(name = "-", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(ChangeDiceAmountCommand(-1))
                return true
            }
        })

        diceLabel = createLabel("Dice: 100")

        val moreDiceBtn = createButton(name = "+", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(ChangeDiceAmountCommand(1))
                return true
            }
        })

        addAmountPanel(lessDiceBtn, diceLabel, moreDiceBtn)
    }

    private fun addLanguageButtons() {
        languageBtn = createButton(name = START_BUTTON_LANGUAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(ChangeLanguageCommand())
                return true
            }
        })

        val cell = addCell(languageBtn)
        setFont(cell.actor)
        row()
    }

    private fun addDebugButton() {
        debugBtn = createButton(name = "MAPS", fontSize = Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(OpenDebugGuiCommand())
                return true
            }
        })

        val cell = addCell(debugBtn)
        setFont(cell.actor)
        row()
    }

    private fun addLoadButton() {
        loadBtn = createButton(name = "LOAD", fontSize = Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(OpenLoadGameCommand())
                return true
            }
        })

        val cell = addCell(loadBtn)
        setFont(cell.actor)
    }

    private fun <T : Actor> addCell(actor: T, colspan: Int = maxSpan): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        cell.colspan(colspan)
        return cell
    }

    private fun updateButtonText() {
        updateStartButtonText()
        updateLanguageButtonText()
        updateWinLabelText()
        updateDiceLabelText()
    }

    fun updateDebugBtnText(text: String) {
        debugBtn.setText(text)
    }

    fun updateLoadBtnText(text: String) {
        loadBtn.setText(text)
    }

    private fun updateWinLabelText() {
        winLabel.setText("Wins: $WIN_AMOUNT")
        pack()
    }

    private fun updateDiceLabelText() {
        diceLabel.setText("Dice: $DICE_MAX")
        pack()
    }

    private fun updateStartButtonText() {
        startBtn.label.setText(START_BUTTON_START)
    }

    private fun updateLanguageButtonText() {
        languageBtn.label.setText(START_BUTTON_LANGUAGE)
    }

}