package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import javax.inject.Inject

abstract class BaseMenu(val guiStage: GameGuiStage,
                        private val previousMenu: BaseMenu? = null)
    : Table(TextureCollection.skin),
        GuiComponent by guiStage,
        DefaultFunction by DEFAULT {

    @Inject
    lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    var isOpen: Boolean = false

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