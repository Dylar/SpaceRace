package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import javax.inject.Inject

abstract class BaseMenu(
        val guiStage: GameGuiStage,
        private val previousMenu: BaseMenu? = null
) : Table(TextureCollection.skin),
        GuiComponent by guiStage {

    @Inject
    lateinit var graphicController: GraphicController

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
        loadData()
        guiStage.addActor(this)
    }

    abstract fun loadData()

    open fun closeMenu() {
        isOpen = false
    }

    open fun onBack() {
        closeMenu()
        previousMenu?.openMenu()
    }

}