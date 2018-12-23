package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.space.control.GameController

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