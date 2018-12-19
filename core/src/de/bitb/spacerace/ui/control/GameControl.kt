package de.bitb.spacerace.ui.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CONTINUE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_STORAGE
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.game.RoundEndMenu
import de.bitb.spacerace.ui.player.ItemMenu

class GameControl(val space: BaseSpace, val guiStage: GameGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage {

    private var itemMenu = ItemMenu(space, guiStage)

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val diceBtn = createButton(name = GAME_BUTTON_DICE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.playerController.dice()
                return true
            }
        })

        val continueBtn = createButton(name = GAME_BUTTON_CONTINUE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.phaseController.nextPhase()
                if (space.phaseController.phase.isNextTurn()) {
                    val endMenu = RoundEndMenu(space, guiStage)
                    endMenu.openMenu()
                    guiStage.addActor(endMenu)
                }
                guiStage.playerStats.update()
                return true
            }
        })

        val storageBtn = createButton(name = GAME_BUTTON_STORAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (itemMenu.isOpen) {
                    itemMenu.closeMenu()
                } else {
                    itemMenu = ItemMenu(space, guiStage)
                    itemMenu.openMenu()
                    guiStage.addActor(itemMenu)
                }
                return true
            }
        })

        setFont(addCell(storageBtn).actor)
        row()
        setFont(addCell(continueBtn).actor)
        row()
        setFont(addCell(diceBtn).actor)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = (SCREEN_WIDTH - width)
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, singlePadding / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

}