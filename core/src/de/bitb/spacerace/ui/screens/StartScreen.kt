package de.bitb.spacerace.ui.screens

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen


class StartScreen(game: BaseGame) : BaseScreen(game) {

    override fun show() {
        super.show()
    }

    override fun render(delta: Float) {
        super.render(delta)
        gameStage.act(delta)
        gameStage.draw()
    }

}
