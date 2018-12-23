package de.bitb.spacerace.ui.screens

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen

class GameOverScreen(game: BaseGame) : BaseScreen(game) {

    override fun render(delta: Float) {
        game.clearScreen(255f,255f)

//        if(Gdx.input.justTouched()){
//            game.screen = GameScreen(game)
//            dispose()
//        }
    }
}