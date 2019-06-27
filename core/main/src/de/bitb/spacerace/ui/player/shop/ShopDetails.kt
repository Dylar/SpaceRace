package de.bitb.spacerace.ui.player.shop

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
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_BUY
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_SELL
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.player.BuyItemCommand
import de.bitb.spacerace.events.commands.player.SellItemCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import org.greenrobot.eventbus.EventBus

class ShopDetails(game: MainGame, guiStage: GameGuiStage, shopMenu: ShopMenu, val item: Item)
    : BaseMenu(guiStage, shopMenu), InputObserver {

    private lateinit var buyBtn: TextButton
    private lateinit var sellBtn: TextButton
    private lateinit var creditsTitle: Cell<Label>

    init {
        MainGame.appComponent.inject(this)
        addTitle(game)
        addImage()
        addText()
        addButtons(game)
        pack()
        setPosition()
    }

    private fun addTitle(game: MainGame) {
        creditsTitle = add("-")
        setCreditsTitle(getPlayerItems(game.gameController.playerController, playerController.currentPlayer.playerColor).getItems(item.itemType).size)
        addPaddingTopBottom(creditsTitle, GAME_MENU_PADDING_SPACE)
        setFont(creditsTitle.actor, GAME_SIZE_FONT_MEDIUM)
        row()
    }

    private fun addImage() {
        val cell = add(item.getDisplayImage(item))
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

        buyBtn = createButton(name = GAME_BUTTON_BUY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(BuyItemCommand(item, playerController.currentPlayerData))
                return true
            }
        })

        var cellBtn = container.add(buyBtn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)

        sellBtn = createButton(name = GAME_BUTTON_SELL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(SellItemCommand(item, playerController.currentPlayerData))
                return true
            }
        })

        cellBtn = container.add(sellBtn)
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

    private fun setCreditsTitle(items: Int) {
        creditsTitle.actor.setText("${item.price} ($items)")
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {
        when (event) {
            is BuyItemCommand,
            is SellItemCommand -> setCreditsTitle(getPlayerItems(game.gameController.playerController, event.playerData.playerColor).getItems(item.itemType).size)
        }
    }

}