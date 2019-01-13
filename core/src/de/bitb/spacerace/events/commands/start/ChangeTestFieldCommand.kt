package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.FieldType

class ChangeTestFieldCommand() : BaseCommand() {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        DEBUG_TEST_FIELD = arrayListOf(FieldType.values()[(DEBUG_TEST_FIELD[0].ordinal + 1) % FieldType.values().size])
    }

}