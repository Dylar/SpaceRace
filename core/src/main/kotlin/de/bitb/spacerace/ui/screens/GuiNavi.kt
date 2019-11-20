package de.bitb.spacerace.ui.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import org.greenrobot.eventbus.EventBus
import java.util.*

interface GuiBackstack {
    fun onBack()
    fun clearBackstack()
    fun addToBackstack(gui: GuiNavi, guiActor: Actor, stage: BaseStage): GuiBackstack
}

object GuiBackstackHandler : GuiBackstack {

    private val backstack = Stack<GuiNavi>()

    private var currentGui: Actor? = null

    private fun getCurrentGui(): GuiNavi? = if (backstack.isEmpty()) null else backstack.peek()
    override fun addToBackstack(
            gui: GuiNavi,
            guiActor: Actor,
            stage: BaseStage
    ): GuiBackstack = this.also {
        val currentGui = getCurrentGui()
        if (currentGui == null || currentGui::class != gui::class) {
            gui.previous = currentGui
            backstack.add(gui)
            Logger.justPrint("Open gui: $gui")
        } else Logger.justPrint("Gui is open: $gui")
        replacePreviousActor(stage, guiActor)
    }

    private fun replacePreviousActor(stage: BaseStage, guiActor: Actor) {
        removePrevious()
        currentGui = guiActor
        stage.addActor(guiActor)
    }

    private fun removePrevious() {
        currentGui?.also { it.remove() }
    }

    override fun onBack() {
        if (backstack.isNotEmpty()) {
            backstack.pop().previous?.also { EventBus.getDefault().post(it) }
        }

        if(backstack.isEmpty()){
            removePrevious()
        }
    }

    override fun clearBackstack() {
        backstack.clear()
        removePrevious()
    }

}

sealed class GuiNavi(
        val player: PlayerColor = PlayerColor.NONE,
        var previous: GuiNavi? = null
) {

    class StorageMenu(player: PlayerColor) : GuiNavi(player)
    class ItemDetailMenu(player: PlayerColor, val itemType: ItemType) : GuiNavi(player)

    class EndRoundMenu : GuiNavi()
    class PlayerEndDetailsMenu(player: PlayerColor) : GuiNavi(player)

    class ObtainShopMenu(player: PlayerColor) : GuiNavi(player)
}
