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
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.ObtainShopCommand
import de.bitb.spacerace.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.events.commands.phases.OpenEndRoundMenuCommand
import de.bitb.spacerace.events.commands.player.BuyItemCommand
import de.bitb.spacerace.events.commands.player.DiceCommand
import de.bitb.spacerace.events.commands.player.SellItemCommand
import de.bitb.spacerace.events.commands.player.UseItemCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.game.RoundEndMenu
import de.bitb.spacerace.ui.player.items.ItemMenu
import de.bitb.spacerace.ui.player.shop.ShopMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class GameControl(game: MainGame,
                  val guiStage: GameGuiStage
) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    private var itemMenu = ItemMenu(game, guiStage)
    private var shopMenu = ShopMenu(game, guiStage)

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val diceBtn = createButton(name = GAME_BUTTON_DICE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(DiceCommand(playerController.currentPlayerData))
                return true
            }
        })

        val continueBtn = createButton(name = GAME_BUTTON_CONTINUE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val gameController = guiStage.gameController
                EventBus.getDefault().post(NextPhaseCommand(playerController.currentPlayerData))
                return true
            }
        })

        val storageBtn = createButton(name = GAME_BUTTON_STORAGE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                openItemMenu(game)
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

    private fun openItemMenu(game: MainGame) {
        if (itemMenu.isOpen) {
            itemMenu.closeMenu()
        } else {
            itemMenu = ItemMenu(game, guiStage)
            itemMenu.openMenu()
            guiStage.addActor(itemMenu)
        }
    }

    private fun openShop(game: MainGame) {
        if (shopMenu.isOpen) {
            shopMenu.closeMenu()
        } else {
            shopMenu = ShopMenu(game, guiStage)
            shopMenu.openMenu()
            guiStage.addActor(shopMenu)
        }
    }

    private fun openEndRoundMenu() {
        val endMenu = RoundEndMenu(guiStage)
        endMenu.openMenu()
        guiStage.addActor(endMenu)
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {
        when (event) {
            is OpenEndRoundMenuCommand -> openEndRoundMenu()
            is ObtainShopCommand -> openShop(game)
            is UseItemCommand -> itemMenu.update(game, event)
            is BuyItemCommand, is SellItemCommand -> shopMenu.update(game, event)
        }
    }

}