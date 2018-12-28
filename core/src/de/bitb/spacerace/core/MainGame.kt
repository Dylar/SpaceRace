package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.ui.screens.start.StartScreen

class MainGame : BaseGame() {

    lateinit var gameController: GameController

    override fun initScreen() {
        setScreen(StartScreen(this))
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

    override fun render() {
        handleSystemInput()
        super.render()
    }

    private fun handleSystemInput() {
        if (isCloseGameTipped()) {
            Gdx.app.exit()
        } else if (isBackTipped()) {
            val previousScreen = (screen as BaseScreen).previousScreen
            gameController.inputHandler.removeListener()
            if (previousScreen != null) {
                changeScreen(previousScreen)
            } else {
                Gdx.app.exit()
            }
        }
    }

    private fun isCloseGameTipped(): Boolean {
        return Gdx.input.isKeyPressed(Input.Keys.SYM) && Gdx.input.isKeyPressed(Input.Keys.W)
    }

    private fun isBackTipped(): Boolean {
        return Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyPressed(Input.Keys.TAB) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)
    }

}