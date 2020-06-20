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
    fun addToBackstack(route: NaviRoute, guiActor: Actor, stage: BaseStage): GuiBackstack
}

object GuiBackstackHandler : GuiBackstack {

    private val backstack = Stack<NaviRoute>()

    private var currentGui: Actor? = null

    private fun getCurrentGui(): NaviRoute? = if (backstack.isEmpty()) null else backstack.peek()
    override fun addToBackstack(
            route: NaviRoute,
            guiActor: Actor,
            stage: BaseStage
    ): GuiBackstack = this.also {
        val currentGui = getCurrentGui()
        if (currentGui == null || currentGui::class != route::class) {
            route.previous = currentGui
            backstack.add(route)
            Logger.justPrint("Open gui: $route")
        } else Logger.justPrint("Gui is open: $route")
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

sealed class NaviRoute(
        val player: PlayerColor = PlayerColor.NONE,
        var previous: NaviRoute? = null
) {

    class StorageMenu(player: PlayerColor) : NaviRoute(player)
    class StorageDetailMenu(player: PlayerColor, val itemType: ItemType) : NaviRoute(player)

    class EndRoundMenu : NaviRoute()
    class PlayerEndDetailsMenu(player: PlayerColor) : NaviRoute(player)

    class ShopMenu(player: PlayerColor) : NaviRoute(player)
    class ShopDetailMenu(player: PlayerColor, val itemType: ItemType) : NaviRoute(player)
}
