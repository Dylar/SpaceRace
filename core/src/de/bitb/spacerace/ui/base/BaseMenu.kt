package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.ui.screens.game.GameGuiStage

abstract class BaseMenu(val guiStage: GameGuiStage, val previousMenu: BaseMenu? = null) : Table(TextureCollection.skin), GuiComponent by guiStage {

    var isOpen: Boolean = false

    val thisPlayer: PlayerColor
        get() = guiStage.gameController.playerController.currentPlayer.playerData.playerColor

    init {
        debug = DEBUG_LAYOUT
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
        pad(GAME_MENU_PADDING)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (!isOpen) {
            remove()
        }
    }

    open fun openMenu() {
        isOpen = true
        previousMenu?.closeMenu()
        guiStage.addActor(this)
    }

    open fun closeMenu() {
        isOpen = false
    }

    open fun onBack() {
        closeMenu()
        previousMenu?.openMenu()
    }
}