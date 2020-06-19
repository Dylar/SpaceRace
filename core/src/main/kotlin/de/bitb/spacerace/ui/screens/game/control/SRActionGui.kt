package de.bitb.spacerace.ui.screens.game.control

import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.core.events.commands.player.DiceCommand
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.screens.GuiNavi
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRActionGui : SRGuiGrid() {

    @Inject
    protected lateinit var observePlayerUseCase: ObserveCurrentPlayerUseCase
    private lateinit var disposable: Disposable

    @Inject
    protected lateinit var playerController: PlayerController

    private lateinit var diceBtn: VisTextButton

    init {
        MainGame.appComponent.inject(this)

        setContent()
        setDimensions()

        initObserver()
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

    private fun initObserver() {
        disposable = observePlayerUseCase.observeStream { player ->
            diceBtn.isDisabled = !player.phase.isMain1() || player.hasDicedEnough()
        }
    }

    private fun setContent() {
        addEmptySlot()
        setStorageBtn()
        setDiceBtn()
        setContinueBtn()
    }

    private fun setStorageBtn() {
        addButton(
                text = GameGuiStrings.GAME_BUTTON_STORAGE,
                listener = {
                    val storageMenu: GuiNavi = GuiNavi.StorageMenu(playerController.currentColor)
                    EventBus.getDefault().post(storageMenu)
                })
    }

    private fun setDiceBtn() {
        addButton(
                text = GameGuiStrings.GAME_BUTTON_DICE,
                listener = { DiceCommand.get(playerController.currentColor).push() })
                .also { diceBtn = it }
    }

    private fun setContinueBtn() {
        addButton(text = GameGuiStrings.GAME_BUTTON_CONTINUE,
                listener = { NextPhaseCommand.get(playerController.currentColor).push() })
    }

    private fun addButton(text: String, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener)
                    .also { addActor(it) }
}