package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.SpaceField

class ObtainFieldCommand(playerColor: PlayerColor, val spaceField: SpaceField) : BaseCommand(playerColor) {

    override fun execute(space: GameController, inputHandler: InputHandler) {
        TODO()
    }

    override fun canExecute(space: GameController): Boolean {
        TODO()
    }

}