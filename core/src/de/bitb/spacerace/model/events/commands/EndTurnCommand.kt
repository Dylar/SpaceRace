package de.bitb.spacerace.model.events.commands

import de.bitb.spacerace.model.space.control.BaseSpace

class EndTurnCommand : BaseCommand() {

    override fun canExecute(space: BaseSpace): Boolean {
        return space.playerController.currentPlayer.playerData.phase.isEndTurn()
//        return space.phaseController.space.phaseController.canContinue()
    }

    override fun execute(space: BaseSpace) {
        space.playerController.nextTurn()
    }


}