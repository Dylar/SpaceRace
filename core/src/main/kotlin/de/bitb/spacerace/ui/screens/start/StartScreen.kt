package de.bitb.spacerace.ui.screens.start

import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.ui.screens.BackgroundStage


class StartScreen : BaseScreen(null) {

    override var allStages: List<Stage> = listOf(BackgroundStage(this), StartGuiStage(this))
    override var inputStages: List<Stage> = listOf(allStages.last())

}
