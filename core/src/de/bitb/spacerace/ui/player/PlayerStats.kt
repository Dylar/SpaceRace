package de.bitb.spacerace.ui.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.Strings.GameGuiStrings.GAME_BUTTON_CREDITS
import de.bitb.spacerace.config.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.Strings.GameGuiStrings.GAME_BUTTON_PHASE
import de.bitb.spacerace.config.Strings.GameGuiStrings.GAME_BUTTON_PLAYER
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.base.GuiComponent

class PlayerStats(val space: BaseSpace, guiStage: GuiComponent = object : GuiComponent {}) : Table(TextureCollection.skin), GuiComponent by guiStage {

    private var shipLabel: Label
    private var creditsLabel: Label

    private var diceLabel: Label
    private var phaseLabel: Label

    init {

        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        add(GAME_BUTTON_PHASE).actor.setFontScale(BaseGuiStage.fontSize)
        add(GAME_BUTTON_DICE).actor.setFontScale(BaseGuiStage.fontSize)
        add(GAME_BUTTON_CREDITS).actor.setFontScale(BaseGuiStage.fontSize)
        add(GAME_BUTTON_PLAYER).actor.setFontScale(BaseGuiStage.fontSize)

        row()

        phaseLabel = createLabel()
        add(phaseLabel)

        diceLabel = createLabel()
        add(diceLabel)

        creditsLabel = createLabel()
        add(creditsLabel)

        shipLabel = createLabel()
        add(shipLabel)

        padRight(BaseGuiStage.singlePadding)
        padLeft(BaseGuiStage.singlePadding)
        pack()

        x = (Gdx.graphics.width - width)
        y = (Gdx.graphics.height - height)
    }

    override fun add(text: CharSequence): Cell<Label> {
        val cell = super.add(text)
        cell.padRight(BaseGuiStage.singlePadding / 2)
        cell.padLeft(BaseGuiStage.singlePadding / 2)
        return cell
    }

    override fun <T : Actor> add(actor: T): Cell<T> {
        val cell = super.add(actor)
        cell.padRight(BaseGuiStage.singlePadding / 2)
        cell.padLeft(BaseGuiStage.singlePadding / 2)
        return cell
    }

    override fun act(delta: Float) {
        val diceResult = if (space.diced) "${(space.diceResult - space.stepsLeft())}/${(space.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)

        phaseLabel.setText(space.phase.name)

        shipLabel.setText(space.currentPlayer.gameColor.name)
        creditsLabel.setText(space.currentPlayer.credits.toString())

        super.act(delta)
    }

}