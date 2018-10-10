package de.bitb.spacerace.core

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.screens.GameOverScreen
import de.bitb.spacerace.screens.GameScreen
import de.bitb.spacerace.screens.StartScreen

class MainGame : BaseGame() {

    override fun initScreen() {
        System.out.println("initScreen")
//        setScreen(StartScreen(this))
        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

    override fun render() {
        super.render()
    }

}