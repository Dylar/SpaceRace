package de.bitb.spacerace.ui.screens.start

import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.screens.game.BackgroundStage


class StartScreen(game: MainGame) : BaseScreen(game, null) {

    override var allStages: List<Stage> = listOf(BackgroundStage(this), StartGuiStage(this))
    override var inputStages: List<Stage> = listOf(allStages.last())

}
