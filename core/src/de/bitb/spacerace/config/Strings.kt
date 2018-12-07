package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.Language.*

object Strings {

    object GameGuiStrings {

        var GAME_BUTTON_PHASE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "PHASE"
                GERMAN -> "PHASE"
            }

        var GAME_BUTTON_DICE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "DICE"
                GERMAN -> "WUERFELN"
            }

        var GAME_BUTTON_CREDITS: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "CREDITS"
                GERMAN -> "KREDITS"
            }

        var GAME_BUTTON_PLAYER: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "PLAYER"
                GERMAN -> "SPIELER"
            }
    }
}