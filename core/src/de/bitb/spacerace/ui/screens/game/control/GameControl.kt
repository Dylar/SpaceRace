package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CONTINUE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_DICE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_STORAGE
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.DiceCommand
import de.bitb.spacerace.events.commands.phases.EndRoundCommand
import de.bitb.spacerace.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.game.RoundEndMenu
import de.bitb.spacerace.ui.player.ItemMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage

class GameControl(game: MainGame, val guiStage: GameGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    private var itemMenu = ItemMenu(game, guiStage)

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val diceBtn = createButton(name = GAME_BUTTON_DICE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val gameController = guiStage.gameController
                gameController.inputHandler.handleCommand(DiceCommand(gameController.playerController.currentPlayer.playerData.playerColor))
                return true
            }
        })

        val continueBtn = createButton(name = GAME_BUTTON_CONTINUE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val gameController = guiStage.gameController
                gameController.inputHandler.handleCommand(NextPhaseCommand(gameController.playerController.currentPlayer.playerData.playerColor))
                return true
            }
        })

        val storageBtn = createButton(name = GAME_BUTTON_STORAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (itemMenu.isOpen) {
                    itemMenu.closeMenu()
                } else {
                    itemMenu = ItemMenu(game, guiStage)
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
        addPaddingTopBottom(cell, Dimensions.GameGuiDimensions.GAME_LABEL_PADDING / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        if (event is EndRoundCommand) {
            val endMenu = RoundEndMenu(guiStage)
            endMenu.openMenu()
            guiStage.addActor(endMenu)
        } else if (event is NextPhaseCommand) {
//            if (gameController.phaseController.phase.isEndTurn()) {
//                val endMenu = RoundEndMenu(gameController, guiStage)
//                endMenu.openMenu()
//                guiStage.addActor(endMenu)
//            }
        }
    }

}