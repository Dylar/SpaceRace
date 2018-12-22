package de.bitb.spacerace.model.events.commands

import de.bitb.spacerace.model.space.control.BaseSpace

class EndRoundCommand : BaseCommand() {

    override fun canExecute(space: BaseSpace): Boolean {
        return space.playerController.isRoundEnd()
    }

    override fun execute(space: BaseSpace) {
        space.playerController.nextRound()
    }

}