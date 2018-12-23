package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.ui.screens.start.StartScreen

class MainGame : BaseGame() {

    override fun initScreen() {
        setScreen(StartScreen(this))
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

    override fun render() {
        super.render()
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.app.exit()
        }
    }

}