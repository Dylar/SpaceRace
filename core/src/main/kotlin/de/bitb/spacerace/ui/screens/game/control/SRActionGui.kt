package de.bitb.spacerace.ui.screens.game.control

import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.core.events.commands.player.DiceCommand
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRActionGui : SRGuiGrid() {

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)

        setContent()
        setDimensions()
    }

    private fun setDimensions() {
        setItemSize(GAME_BUTTON_WIDTH_DEFAULT, GAME_BUTTON_HEIGHT_DEFAULT)
        setGuiBorder(
                columns = 2f,
                rows = 2f,
                guiPosX = SCREEN_WIDTH,
                alignHoriz = SRAlign.RIGHT,
                alignVert = SRAlign.BOTTOM
        )
    }

    private fun setContent() {
        addEmptySlot()
        setStorageBtn()
        setDiceBtn()
        setContinueBtn()
    }

    private fun setStorageBtn() {
        addButton(
                text = "Storage",
                listener = {
                    val storageMenu: GuiNavi = GuiNavi.StorageMenu(playerController.currentColor)
                    EventBus.getDefault().post(storageMenu)
                })
    }

    private fun setDiceBtn() {
        addButton(
                text = "Dice",
                listener = { EventBus.getDefault().post(DiceCommand.get(playerController.currentColor)) })
    }

    private fun setContinueBtn() {
        addButton(text = "Continue",
                listener = { EventBus.getDefault().post(NextPhaseCommand.get(playerController.currentColor)) })
    }

    private fun addButton(text: String, listener: () -> Unit) {
        createTextButtons(
                text = text,
                listener = listener)
                .also { addActor(it) }
    }
}