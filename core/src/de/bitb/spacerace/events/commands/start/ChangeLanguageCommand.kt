package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.core.MainGame

class ChangeLanguageCommand(playerColor: PlayerColor = PlayerColor.NONE) : StartCommand(playerColor) {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        LANGUAGE = when (LANGUAGE) {
            Language.ENGLISH -> Language.GERMAN
            Language.GERMAN -> Language.ENGLISH
        }
    }

}