package de.bitb.spacerace.controller

import de.bitb.spacerace.model.events.BaseCommand

class InputHandler {

    fun <T : BaseCommand> handleCommand(command: T) {
        command.execute()
    }
}