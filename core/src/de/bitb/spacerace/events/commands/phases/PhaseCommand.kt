package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.events.commands.BaseCommand

abstract class PhaseCommand(val inputHandler: InputHandler, playerColor: PlayerColor) : BaseCommand(playerColor ) {


}