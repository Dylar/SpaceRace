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
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.events.ObtainShopEvent
import de.bitb.spacerace.core.events.OpenEndRoundMenuEvent
import de.bitb.spacerace.core.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.core.events.commands.player.DiceCommand
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.game.RoundEndMenu
import de.bitb.spacerace.ui.player.items.ItemMenu
import de.bitb.spacerace.ui.player.shop.ShopMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class GameActionGui(
        val guiStage: GameGuiStage
) : Table(TextureCollection.skin),
        GuiComponent by guiStage {

    private lateinit var itemMenu: ItemMenu //= ItemMenu(guiStage, NONE_PLAYER_DATA)
    private lateinit var shopMenu: ShopMenu //= ShopMenu(guiStage)

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
        EventBus.getDefault().register(this)

        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val diceBtn = createButton(name = GAME_BUTTON_DICE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(DiceCommand.get(playerController.currentColor))
                return true
            }
        })

        val continueBtn = createButton(name = GAME_BUTTON_CONTINUE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(NextPhaseCommand.get(playerController.currentColor))
                return true
            }
        })

        val storageBtn = createButton(name = GAME_BUTTON_STORAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                openItemMenu()
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openEndRoundMenuEvent(event: OpenEndRoundMenuEvent) {
        openEndRoundMenu()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openShopEvent(event: ObtainShopEvent) {
        openShop()
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

    private fun openItemMenu() {
        if (!::itemMenu.isInitialized) {
            itemMenu = ItemMenu(guiStage, playerController.currentPlayerData)
        }
        if (itemMenu.isOpen) {
            itemMenu.closeMenu()
        } else {
            itemMenu = ItemMenu(guiStage, playerController.currentPlayerData)
            itemMenu.openMenu(playerController.currentPlayerData)
            guiStage.addActor(itemMenu)
        }
    }

    private fun openShop() {
        if (!::shopMenu.isInitialized) {
            shopMenu = ShopMenu(guiStage)
        }
        if (shopMenu.isOpen) {
            shopMenu.closeMenu()
        } else {
            shopMenu = ShopMenu(guiStage)
            shopMenu.openMenu(playerController.currentPlayerData)
            guiStage.addActor(shopMenu)
        }
    }

    private fun openEndRoundMenu() {
        val endMenu = RoundEndMenu(guiStage)
        endMenu.openMenu(playerController.currentPlayerData)
        guiStage.addActor(endMenu)
    }

    override fun clear() {
        EventBus.getDefault().unregister(this)
        super.clear()
    }
}