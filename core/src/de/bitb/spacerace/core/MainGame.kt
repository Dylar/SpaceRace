package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.control.TestSpace
import de.bitb.spacerace.ui.screens.start.StartScreen

class MainGame : BaseGame() {

    lateinit var gameController: GameController

    override fun initScreen() {
        gameController = TestSpace(this)
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