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
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.player.UseItemCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.items.equip.EquipItem
import de.bitb.spacerace.model.items.usable.UsableItem
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.BaseMenu

class ItemDetails(game: MainGame, guiStage: GameGuiStage, itemMenu: ItemMenu, val items: MutableList<Item>) : BaseMenu(guiStage, itemMenu), InputObserver {

    private lateinit var useBtn: TextButton
    private lateinit var unuseBtn: TextButton
    private lateinit var usedTitle: Cell<Label>

    init {
        addTitle()
        addImage()
        addText()
        addButtons(game)
        pack()
        setPosition()
        setUsedTitle()
        setUseButton()
    }

    private fun addTitle() {
        usedTitle = add("-")
        setUsedTitle()
        addPaddingTopBottom(usedTitle, GAME_MENU_PADDING_SPACE)
        setFont(usedTitle.actor, GAME_SIZE_FONT_MEDIUM)
        row()
    }

    private fun addImage() {
        val cell = add(items[0].getDisplayImage(items[0].img))
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText() {
        row()
        val cell = add(items[0].text)
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

        if (items[0] is EquipItem) {
            unuseBtn = createButton(name = GAME_BUTTON_USE, listener = object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    val item = getByState(ItemState.EQUIPPED)
                    if (item.isNotEmpty()) {
                        game.gameController.inputHandler.handleCommand(UseItemCommand(item[0], game.gameController.playerController.currentPlayer.playerData.playerColor))
                    }
                    return true
                }
            })
            addButton(container, unuseBtn)
        }

        useBtn = createButton(name = GAME_BUTTON_USE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val item = getByState(ItemState.STORAGE)
                if (item.isNotEmpty()) {
                    game.gameController.inputHandler.handleCommand(UseItemCommand(item[0], getCurrentPlayer(game).playerData.playerColor))
                }
                return true
            }
        })
        addButton(container, useBtn)

        val cancelBtn = createButton(name = GAME_BUTTON_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onBack()
                return true
            }
        })
        addButton(container, cancelBtn)
        setUsedTitle()
        setUseButton()
        setUnuseButton()
    }

    private fun addButton(container: Table, btn: TextButton) {
        val cellBtn = container.add(btn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)
    }

    private fun getStateCount(itemState: ItemState): Int {
        return getByState(itemState).size
    }

    private fun getByState(itemState: ItemState): MutableList<Item> {
        val list = ArrayList<Item>()
        items.forEach { if (it.state == itemState) list.add(it) }
        return list
    }

    private fun setUsedTitle() {
        val text = when (items[0]) {
            is EquipItem -> "EQUIPPED (${getStateCount(ItemState.EQUIPPED)}/${items.size})"
            else -> "STORAGE (${getStateCount(ItemState.STORAGE)})"
        }
        usedTitle.actor.setText(text)
    }

    private fun setUseButton() {
        val text = when (items[0]) {
            is EquipItem -> "EQUIP"
            is DisposableItem -> "DISPOSE"
            is UsableItem -> "USE"
            else -> "-"
        }
        useBtn.setText(text)

    }

    private fun setUnuseButton() {
        if (::unuseBtn.isInitialized) {
            val text = when (items[0]) {
                is EquipItem -> "UNEQUIP"
                else -> "-"
            }
            unuseBtn.setText(text)
        }
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {
        if (event is UseItemCommand) {
            setUsedTitle()
            setUseButton()
            setUnuseButton()
        }
    }

}