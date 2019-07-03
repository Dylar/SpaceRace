package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.FieldType

class SelectTestFieldCommand(private val testField: FieldType) : BaseCommand() {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        if (!DEBUG_TEST_FIELD.remove(testField)) {
            DEBUG_TEST_FIELD.add(testField)
        }
    }

}