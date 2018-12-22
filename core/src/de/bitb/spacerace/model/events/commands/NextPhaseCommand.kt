package de.bitb.spacerace.model.events.commands

import de.bitb.spacerace.model.space.control.BaseSpace

class NextPhaseCommand : BaseCommand() {
    override fun canExecute(space: BaseSpace): Boolean {
        return space.phaseController.canContinue()
    }

    override fun execute(space: BaseSpace) {
        space.phaseController.nextPhase()
    }

}