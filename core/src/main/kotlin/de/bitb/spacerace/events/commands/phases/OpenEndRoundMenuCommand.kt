package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.events.commands.BaseCommand

class OpenEndRoundMenuCommand() : BaseCommand(NONE_PLAYER_DATA) {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {

    }

}