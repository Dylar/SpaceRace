package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.usecase.game.observe.ObservePlayerUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class SRMenu(
        private val backstackHandler: GuiBackstack = GuiBackstackHandler
) : Table(TextureCollection.skin),
        GuiBackstack by backstackHandler {

    @Inject
    lateinit var graphicController: GraphicController

    @Inject
    lateinit var observePlayerUseCase: ObservePlayerUseCase
    private var disposable: Disposable? = null

    init {
//        MainGame.appComponent.inject(this)

        debug = DEBUG_LAYOUT
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
        pad(GAME_MENU_PADDING)
    }

    override fun onBack() {
        disposable?.dispose()
        remove()
        backstackHandler.onBack()
    }

}