package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_GUI_PADDING
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.usecase.game.observe.ObservePlayerUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseMenu(
        val guiStage: GameGuiStage,
        private val previousMenu: BaseMenu? = null,
        var player: PlayerData? = null
) : Table(TextureCollection.skin),
        GuiComponent by guiStage {

    @Inject
    lateinit var graphicController: GraphicController

    @Inject
    lateinit var observePlayerUseCase: ObservePlayerUseCase
    private var disposable: Disposable? = null

    var isOpen: Boolean = false

    init {
        MainGame.appComponent.inject(this)
        initObserver()

        debug = DEBUG_LAYOUT
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
        pad(GAME_GUI_PADDING)
    }

    private fun initObserver() {
        player?.also {
            disposable = observePlayerUseCase.observeStream(it.playerColor) { player ->
                this.player = player
                refreshMenu()
            }
        }
    }

    abstract fun refreshMenu()

    override fun act(delta: Float) {
        super.act(delta)
        if (!isOpen) {
            remove()
        }
    }

    open fun openMenu(player: PlayerData) {
        this.player = player
        isOpen = true
        previousMenu?.closeMenu()
        guiStage.addActor(this)
    }

    open fun closeMenu() {
        disposable?.dispose()
        isOpen = false
    }

    fun onBackOLD() {
        closeMenu()
        previousMenu?.openMenu(player!!)
    }

}