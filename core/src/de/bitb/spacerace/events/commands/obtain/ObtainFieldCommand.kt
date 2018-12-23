package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.model.space.fields.SpaceField

class ObtainFieldCommand(playerColor: PlayerColor, val spaceField: SpaceField) : BaseCommand(playerColor) {

    override fun execute(space: BaseSpace) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canExecute(space: BaseSpace): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}