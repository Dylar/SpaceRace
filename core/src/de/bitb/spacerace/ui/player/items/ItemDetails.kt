package de.bitb.spacerace.ui.player.items

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING_SPACE
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_USE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENUITEM_TITLE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENU_ITEM_DETAILS_TITLE_USABLE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENU_ITEM_DETAILS_TITLE_USED
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.player.UseItemCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.BaseMenu

class ItemDetails(game: MainGame, guiStage: GameGuiStage, itemMenu: ItemMenu, val item: Item) : BaseMenu(guiStage, itemMenu), InputObserver {

    private lateinit var useBtn: TextButton
    private lateinit var usedTitle: Cell<Label>

    init {
        addTitle()
        addImage()
        addText()
        addButtons(game)
        pack()
        setPosition()
    }

    private fun addTitle() {
        usedTitle = add("-")
        setUsedTitle()
        addPaddingTopBottom(usedTitle, GAME_MENU_PADDING_SPACE)
        setFont(usedTitle.actor, GAME_SIZE_FONT_MEDIUM)
        row()
    }

    private fun addImage() {
        val cell = add(item.getDisplayImage())
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText() {
        row()
        val cell = add(item.text)
        addPaddingTopBottom(cell, GAME_MENU_PADDING_SPACE)
        setFont(cell.actor, GAME_SIZE_FONT_SMALL)
    }

    private fun setPosition() {
        x = (Dimensions.SCREEN_WIDTH - (Dimensions.SCREEN_WIDTH / 2) - width / 2)
        y = (Dimensions.SCREEN_HEIGHT - (Dimensions.SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addButtons(game: MainGame) {
        row()

        val container = Table(skin)
        val cell = add(container)
        cell.expandX()

        useBtn = createButton(name = GAME_BUTTON_USE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.gameController.inputHandler.handleCommand(UseItemCommand(item, game.gameController.playerController.currentPlayer.playerData.playerColor))
                return true
            }
        })

        var cellBtn = container.add(useBtn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)

        val cancelBtn = createButton(name = GAME_BUTTON_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onBack()
                return true
            }
        })
        cellBtn = container.add(cancelBtn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)
    }

    private fun setUsedTitle() {
        usedTitle.actor.setText(if (item.state == ItemState.USED) GAME_MENU_ITEM_DETAILS_TITLE_USED else GAME_MENU_ITEM_DETAILS_TITLE_USABLE)
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        if (event is UseItemCommand) {
            setUsedTitle()
        }
    }
}