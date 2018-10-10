package de.bitb.spacerace.screens

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.model.Asteroid


class StartScreen(game: BaseGame) : BaseScreen(game) {

    override fun show() {
        super.show()
        stage.addActor(Asteroid())
    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.act(delta)
        stage.draw()
    }

}
