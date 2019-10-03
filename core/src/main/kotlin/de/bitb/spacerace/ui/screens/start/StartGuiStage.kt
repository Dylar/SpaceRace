package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_LOAD
import de.bitb.spacerace.events.OpenDebugGuiEvent
import de.bitb.spacerace.events.OpenLoadGameEvent
import de.bitb.spacerace.ui.screens.start.control.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class StartGuiStage(
        screen: StartScreen
) : BaseGuiStage(screen) {

    private var startButtonControl = StartButtonGui(this)

    private var playerSelection = PlayerSelectionGui(this)
    private var loadGameSelection = LoadGameGui(this)

    private var mapSelection = MapSelectionGui(this)
    private var fieldSelectionControl = TestFieldSelectionGui(this)

    init {
        EventBus.getDefault().register(this)

        startButtonControl.setPosition(Dimensions.SCREEN_WIDTH / 2 - startButtonControl.width / 2, Dimensions.SCREEN_HEIGHT / 1.5f - startButtonControl.height / 2)
        playerSelection.setPosition(0f, Dimensions.SCREEN_HEIGHT / 2f - playerSelection.height / 2)
        mapSelection.setPosition(Dimensions.SCREEN_WIDTH - mapSelection.width, Dimensions.SCREEN_HEIGHT / 2f - mapSelection.height / 2)
        fieldSelectionControl.setPosition(Dimensions.SCREEN_WIDTH - fieldSelectionControl.width, Dimensions.SCREEN_HEIGHT / 2f - fieldSelectionControl.height / 2)

        addActor(startButtonControl)
        addActor(playerSelection)
        addActor(fieldSelectionControl)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openLoadGameEvent(event: OpenLoadGameEvent) {
        changeMenu(loadGameSelection, START_BUTTON_LOAD, playerSelection, "PLAYER")
                .also { startButtonControl.updateLoadBtnText(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun openDebugGuiEvent(event: OpenDebugGuiEvent) {
        changeMenu(mapSelection, "MAPS", fieldSelectionControl, "FIELDS")
                .also { startButtonControl.updateDebugBtnText(it) }
    }

    private fun changeMenu(menu1: BaseGuiControl,
                           menu1Text: String,
                           menu2: BaseGuiControl,
                           menu2Text: String): String {
        val openMenu1 = menu1.stage == null
        addActor(if (openMenu1) menu1 else menu2)
        (if (openMenu1) menu2 else menu1).remove()
        return if (openMenu1) menu2Text else menu1Text

    }

    override fun clear() {
        EventBus.getDefault().unregister(this)
        super.clear()
    }
}