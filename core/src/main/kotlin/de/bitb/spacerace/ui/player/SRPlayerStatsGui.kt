package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_WIDTH_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_MODS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_LABEL_PLAYER_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.database.items.getModifierValues
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import javax.inject.Inject

class SRPlayerStatsGui : VisTable(), GuiBuilder {

    @Inject
    protected lateinit var playerController: PlayerController
    @Inject
    protected lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase

    private lateinit var playerAmountLabel: Label
    private lateinit var creditsLabel: Label

    private lateinit var diceLabel: Label
    private lateinit var diceModLabel: Label
    private lateinit var phaseLabel: Label

    init {
        MainGame.appComponent.inject(this)

        setBackgroundByPath(IMAGE_PATH_GUI_BACKGROUND)
        setContent()
        setDimensions()
        initObserver()
    }

    private fun initObserver() {
        //TODO dispose
        observeCurrentPlayerUseCase.observeStream {
            update(it)
        }
    }

    private fun setDimensions() {
        alignGui(guiPosY = SCREEN_HEIGHT,
                guiWidth = prefWidth,
                guiHeight = prefHeight,
                alignHoriz = SRAlign.LEFT,
                alignVert = SRAlign.TOP)
    }

    private fun setContent() {
        listOf(GAME_LABEL_PLAYER_AMOUNT,
                GAME_BUTTON_PHASE,
                GAME_BUTTON_DICE,
                GAME_BUTTON_MODS,
                GAME_BUTTON_CREDITS)
                .forEach { addLabel(it) }
        row()
        playerAmountLabel = addLabel()
        phaseLabel = addLabel()
        diceLabel = addLabel()
        diceModLabel = addLabel()
        creditsLabel = addLabel()
    }

    private fun addLabel(text: String = "") =
            createLabel(text = text, fontColor = Color.TEAL)
                    .also {
                        it.setAlignment(Align.center)
                        add(it).width(GAME_LABEL_WIDTH_DEFAULT)
                                .height(GAME_LABEL_HEIGHT_DEFAULT)
                    }


    //UPDATE

    fun update(playerData: PlayerData) {
        updatePlayerAmount(playerData)
        updateCredits(playerData)
        updateRound(playerData.playerColor)
        updateDice(playerData)
        updateDiceMod(playerData)
        updatePhase(playerData.phase)
        pack()
    }

    private fun updateCredits(playerData: PlayerData) {
        creditsLabel.setText(playerData.credits.toString())
    }

    private fun updateRound(playerColor: PlayerColor) {
        setFont(playerAmountLabel, fontColor = playerColor.color)
        setFont(diceLabel, fontColor = playerColor.color)
        setFont(diceModLabel, fontColor = playerColor.color)
        setFont(phaseLabel, fontColor = playerColor.color)
        setFont(creditsLabel, fontColor = playerColor.color)
    }

    private fun setFont(label: Label, fontColor: Color) {
        val style = label.style
        style.fontColor = fontColor
        label.style = style
    }

    private fun updateDice(playerData: PlayerData) {
        val maxSteps = playerData.getMaxSteps()
        val diceResult = "${playerData.stepsLeft()}/$maxSteps"
        diceLabel.setText(diceResult)
    }

    private fun updateDiceMod(playerData: PlayerData) {
        val (mod, add) = playerData.getModifierValues()
        diceModLabel.setText("${"%.1f".format(mod)} / $add")
    }

    private fun updatePhase(phase: Phase) {
        phaseLabel.setText(phase.text)
    }

    private fun updatePlayerAmount(playerData: PlayerData) {
        val currentIndex = playerController.getPlayerIndex(playerData.playerColor) + 1
        val maxPlayer = playerController.players.size
        playerAmountLabel.setText("$currentIndex/$maxPlayer")
    }

}