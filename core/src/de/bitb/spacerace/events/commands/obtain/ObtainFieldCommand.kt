package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.SpaceField

class ObtainFieldCommand(playerColor: PlayerColor, val spaceField: SpaceField) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        TODO()
    }

    override fun execute(game: MainGame) {
        TODO()
    }

}