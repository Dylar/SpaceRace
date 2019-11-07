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
import de.bitb.spacerace.core.events.commands.player.BuyItemCommand
import de.bitb.spacerace.core.events.commands.player.SellItemCommand
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.items.*
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import org.greenrobot.eventbus.EventBus

class ShopDetails(
        guiStage: GameGuiStage,
        shopMenu: ShopMenu,
        private val itemType: ItemType,
        player:PlayerData?
) : BaseMenu(guiStage, shopMenu, player) {

    private val itemGraphic: ItemGraphic = itemType.createGraphic()

    private lateinit var buyBtn: TextButton
    private lateinit var sellBtn: TextButton
    private lateinit var creditsTitle: Cell<Label>

    init {
        addTitle()
        addImage()
        addText()
        addButtons()
        pack()
        setPosition()
    }

    private fun addTitle() {
        creditsTitle = add("-")
        val amount = player!!.storageItems.filter { it::class == itemType::class }.size
        setCreditsTitle(amount)
        addPaddingTopBottom(creditsTitle, GAME_MENU_PADDING_SPACE)
        setFont(creditsTitle.actor, GAME_SIZE_FONT_MEDIUM)
        row()
    }

    private fun addImage() {
        val cell = add(itemGraphic.getDisplayImage())
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText() {
        row()
        val cell = add(itemType.getText())
        addPaddingTopBottom(cell, GAME_MENU_PADDING_SPACE)
        setFont(cell.actor, GAME_SIZE_FONT_SMALL)
    }

    private fun setPosition() {
        x = (Dimensions.SCREEN_WIDTH - (Dimensions.SCREEN_WIDTH / 2) - width / 2)
        y = (Dimensions.SCREEN_HEIGHT - (Dimensions.SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addButtons() {
        row()

        val container = Table(skin)
        val cell = add(container)
        cell.expandX()

        buyBtn = createButton(name = GAME_BUTTON_BUY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(BuyItemCommand.get(itemType, player!!.playerColor))
                return true
            }
        })

        var cellBtn = container.add(buyBtn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)

        sellBtn = createButton(name = GAME_BUTTON_SELL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(SellItemCommand(itemType, player!!.playerColor))
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
        creditsTitle.actor.setText("${itemType.getDefaultInfo().price} ($items)")
    }

    override fun refreshMenu() {
        val itemCount = player!!.storageItems
                .filter { it.itemInfo.type == itemGraphic.itemType }.size
        setCreditsTitle(itemCount)
    }

}