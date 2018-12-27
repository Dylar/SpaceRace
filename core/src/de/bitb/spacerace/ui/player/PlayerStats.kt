package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.DiceCommand
import de.bitb.spacerace.events.commands.MoveCommand
import de.bitb.spacerace.events.commands.obtain.ObtainLoseCommand
import de.bitb.spacerace.events.commands.obtain.ObtainWinCommand
import de.bitb.spacerace.events.commands.phases.EndTurnCommand
import de.bitb.spacerace.events.commands.phases.PhaseCommand
import de.bitb.spacerace.events.commands.phases.StartMain1Command
import de.bitb.spacerace.events.commands.phases.StartMain2Command
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.ui.base.GuiComponent

class PlayerStats(private val guiStage: BaseGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

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

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        val playerData = game.gameController.playerController.currentPlayer.playerData
//        when (event) {
//            is MoveCommand, is DiceCommand -> updateDice(playerData)
//            is PhaseCommand -> {
//                updateRound(playerData.playerColor)
//                updatePhase(playerData.phase)
//                if (event is EndTurnCommand) {
//                    updateDice(playerData)
//                    updateCredits(playerData)
//                }
//            }
//            is StartGameCommand -> update(playerData)
//            is StartMain1Command -> {
//                updateCredits(playerData)
//                updateDice(playerData)
//            }
//        }
        update(playerData) //TODO update not all
    }

    private fun updateCredits(playerData: PlayerData) {
        creditsLabel.setText(playerData.credits.toString())
    }

    private fun updateRound(playerColor: PlayerColor) {
        setFont(phaseLabel, fontColor = playerColor.color)
        setFont(diceLabel, fontColor = playerColor.color)
        setFont(creditsLabel, fontColor = playerColor.color)
    }

    private fun updateDice(playerData: PlayerData) {
        val diceResult = if (playerData.diced) "${(playerData.diceResult - playerData.stepsLeft())}/${(playerData.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)
    }

    private fun updatePhase(phase: Phase) {
        phaseLabel.setText(phase.text)
    }

    fun update(playerData: PlayerData) {
        updateCredits(playerData)
        updateRound(playerData.playerColor)
        updateDice(playerData)
        updatePhase(playerData.phase)
        pack()
    }

}