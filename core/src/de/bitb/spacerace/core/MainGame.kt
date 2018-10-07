package de.bitb.spacerace.core

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.screens.GameScreen

class MainGame : BaseGame() {

    override fun initScreen() {
        System.out.println("initScreen")
        setScreen(GameScreen(this))
    }

    override fun render() {
        super.render()
    }

}