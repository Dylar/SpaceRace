package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor

class SelectPlayerCommand(
        var playerColor: PlayerColor
) : BaseCommand(NONE_PLAYER_DATA) {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        if (!SELECTED_PLAYER.remove(playerColor)) {
            SELECTED_PLAYER.add(playerColor)
        }
    }

}