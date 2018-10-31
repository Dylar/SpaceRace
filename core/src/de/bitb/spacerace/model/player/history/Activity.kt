package de.bitb.spacerace.model.player.history

import com.badlogic.gdx.scenes.scene2d.Actor

abstract class Activity : Actor() {
    lateinit var turn: Turn

    abstract fun doIt()
}