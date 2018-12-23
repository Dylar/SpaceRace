package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.model.space.fields.SpaceField

class ObtainMineCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(space: BaseSpace): Boolean {
        return true
    }

    override fun execute(space: BaseSpace) {
        space.fieldController.activateMine(space.playerController.getPlayer(playerColor))
    }

}