package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.DiceCommand
import de.bitb.spacerace.events.commands.MoveCommand
import de.bitb.spacerace.events.commands.phases.*
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.base.GuiComponent

class PlayerStats(val space: GameController, guiComponent: GuiComponent = object : GuiComponent {}) : Table(TextureCollection.skin), GuiComponent by guiComponent, InputObserver {

    private var creditsLabel: Label

    private var diceLabel: Label
    private var phaseLabel: Label

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        setFont(add(GAME_BUTTON_PHASE).actor)
        setFont(add(GAME_BUTTON_DICE).actor)
        setFont(add(GAME_BUTTON_CREDITS).actor)

        row()

        phaseLabel = add("-").actor
        setFont(phaseLabel)

        diceLabel = add("-").actor
        setFont(diceLabel)

        creditsLabel = add("-").actor
        setFont(creditsLabel)

        pack()

        x = (SCREEN_WIDTH - width)
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

    override fun <T : BaseEvent> update(event: T) {
        if (event is MoveCommand || event is DiceCommand) {
            updateDice()
        } else if (event is PhaseCommand) {
            updateRound()
            updatePhase()
            if (event is EndTurnCommand) {
                updateDice()
            }
        }
    }

    private fun updateCredits(playerData: PlayerData = space.playerController.currentPlayer.playerData) {
        creditsLabel.setText(playerData.credits.toString())
    }

    private fun updateRound(playerColor: PlayerColor = space.playerController.currentPlayer.playerData.playerColor) {
        setFont(phaseLabel, fontColor = playerColor.color)
        setFont(diceLabel, fontColor = playerColor.color)
        setFont(creditsLabel, fontColor = playerColor.color)
    }

    private fun updateDice(playerData: PlayerData = space.playerController.currentPlayer.playerData) {
        val diceResult = if (playerData.diced) "${(playerData.diceResult - playerData.stepsLeft())}/${(playerData.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)
    }

    private fun updatePhase(phase: Phase = space.playerController.currentPlayer.playerData.phase) {
        phaseLabel.setText(phase.text)
    }

    fun update() {
        val playerData = space.playerController.currentPlayer.playerData
        updateCredits(playerData)
        updateRound(playerData.playerColor)
        updateDice(playerData)
        updatePhase()
        pack()
    }

}