package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import javax.inject.Inject

abstract class BaseMenu(val guiStage: GameGuiStage,
                        private val previousMenu: BaseMenu? = null)
    : Table(TextureCollection.skin),
        GuiComponent by guiStage, InputObserver,
        DefaultFunction by object : DefaultFunction {} {

    @Inject
    lateinit var inputHandler: InputHandler

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
        inputHandler.addListener(this)
        isOpen = true
        previousMenu?.closeMenu()
        guiStage.addActor(this)
    }

    open fun closeMenu() {
        inputHandler.removeListener(this)
        isOpen = false
    }

    open fun onBack() {
        closeMenu()
        previousMenu?.openMenu()
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {

    }
}