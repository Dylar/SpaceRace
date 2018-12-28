package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.maps.SpaceMaps

class SelectMapCommand(val spaceMaps: SpaceMaps) : BaseCommand() {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        game.gameController.spaceMap = spaceMaps
    }

}