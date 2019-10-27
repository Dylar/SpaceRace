package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_MODS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_LABEL_PLAYER_AMOUNT
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.getModifierValues
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.GuiComponent
import javax.inject.Inject

class PlayerStatsGui(
        private val guiStage: BaseGuiStage
) : Table(TextureCollection.skin),
        GuiComponent by guiStage {

    @Inject
    protected lateinit var playerController: PlayerController

    @Inject
    protected lateinit var graphicController: GraphicController

    private var playerAmountLabel: Label
    private var creditsLabel: Label

    private var diceLabel: Label
    private var diceModLabel: Label
    private var phaseLabel: Label

    init {
        MainGame.appComponent.inject(this)

        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        setFont(add(GAME_LABEL_PLAYER_AMOUNT).actor)
        setFont(add(GAME_BUTTON_MODS).actor)
        setFont(add(GAME_BUTTON_DICE).actor)
        setFont(add(GAME_BUTTON_PHASE).actor)
        setFont(add(GAME_BUTTON_CREDITS).actor)

        row()

        playerAmountLabel = add("0/10").actor
        setFont(playerAmountLabel)

        diceModLabel = add("0.0 / 0").actor
        setFont(diceModLabel)

        diceLabel = add("-").actor
        setFont(diceLabel)

        phaseLabel = add("-").actor
        setFont(phaseLabel)

        creditsLabel = add("000000000").actor
        setFont(creditsLabel)

        pack()

        x = 0f //(SCREEN_WIDTH - width) + (SCREEN_WIDTH - width)/2
        y = (SCREEN_HEIGHT - height)
    }

    override fun add(text: CharSequence): Cell<Label> {
        val cell = super.add(text)
        addPaddingLeftRight(cell)
        return cell
    }

    override fun <T : Actor> add(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingLeftRight(cell)
        return cell
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

    private fun updateDice(playerData: PlayerData) {
        val maxSteps = playerData.getMaxSteps()
        val diceResult = "${playerData.stepsLeft()}/$maxSteps"
        diceLabel.setText(diceResult)
    }

    private fun updateDiceMod(playerData: PlayerData) {
        playerData.getModifierValues()
                .also { (mod, add) ->
                    diceModLabel.setText("${"%.1f".format(mod)} / $add")
                }
    }

    private fun updatePhase(phase: Phase) {
        phaseLabel.setText(phase.text)
    }

    fun update(playerData: PlayerData) {
        updatePlayerAmount(playerData)
        updateCredits(playerData)
        updateRound(playerData.playerColor)
        updateDice(playerData)
        updateDiceMod(playerData)
        updatePhase(playerData.phase)
        pack()
    }

    private fun updatePlayerAmount(playerData: PlayerData) {
        val currentIndex = playerController.getPlayerIndex(playerData.playerColor) + 1
        val maxPlayer = playerController.players.size
        playerAmountLabel.setText("$currentIndex/$maxPlayer")
    }

}