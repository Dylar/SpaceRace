package de.bitb.spacerace.base

import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.base.GuiComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseGuiStage(val screen: BaseScreen,
                            viewport: Viewport = ScreenViewport()) : BaseStage(viewport), GuiComponent {

    @Inject
    lateinit var inputHandler: InputHandler

    val gameController: GameController = screen.game.gameController

    init {
        MainGame.appComponent.inject(this)
    }
}