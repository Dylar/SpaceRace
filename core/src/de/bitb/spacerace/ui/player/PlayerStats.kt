package de.bitb.spacerace.ui.player

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.ui.base.GuiComponent

class PlayerStats(val space: BaseSpace, guiComponent: GuiComponent = object : GuiComponent {}) : Table(TextureCollection.skin), GuiComponent by guiComponent {
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

        update()
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

    fun update() {
        phaseLabel.setText(space.phaseController.phase.text)

        val player = space.playerController.currentPlayer
        val playerColor = player.playerColor

        creditsLabel.setText(player.credits.toString())

        setFont(phaseLabel, fontColor = playerColor.color)
        setFont(diceLabel, fontColor = playerColor.color)
        setFont(creditsLabel, fontColor = playerColor.color)
    }

    override fun act(delta: Float) {
        super.act(delta)
        val diceResult = if (space.playerController.diced) "${(space.playerController.diceResult - space.playerController.stepsLeft())}/${(space.playerController.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)
    }

}