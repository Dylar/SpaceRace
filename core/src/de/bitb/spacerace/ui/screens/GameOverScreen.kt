package de.bitb.spacerace.ui.screens

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.core.MainGame

class GameOverScreen(game: MainGame) : BaseScreen(game, null) {

    override fun render(delta: Float) {
        game.clearScreen(255f,255f)

//        if(Gdx.input.justTouched()){
//            game.screen = GameScreen(game)
//            dispose()
//        }
    }
}