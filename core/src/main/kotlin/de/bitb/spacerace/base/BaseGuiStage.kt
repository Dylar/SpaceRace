package de.bitb.spacerace.base

import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.base.GuiComponent

abstract class BaseGuiStage(val screen: BaseScreen,
                            viewport: Viewport = ScreenViewport()
) : BaseStage(viewport), GuiComponent {

    init {
        MainGame.appComponent.inject(this)
    }
}