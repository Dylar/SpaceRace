package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA

class ChangeLanguageCommand() : StartScreenCommand(NONE_PLAYER_DATA) {
    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        LANGUAGE = when (LANGUAGE) {
            Language.ENGLISH -> Language.GERMAN
            Language.GERMAN -> Language.ENGLISH
        }
    }

}