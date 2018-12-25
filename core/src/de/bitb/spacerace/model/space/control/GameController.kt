package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame

abstract class GameController(game: MainGame) {
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    val inputHandler = InputHandler(game)
    val fieldController = FieldController()
    val phaseController = PhaseController()
    val playerController = PlayerController()


    open fun createSpace(game: MainGame) {
        fieldController.connections = ConnectionList(game.gameController)
    }

}

