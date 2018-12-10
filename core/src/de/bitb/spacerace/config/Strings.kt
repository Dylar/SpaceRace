package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.Language.*

object Strings {

    object GameGuiStrings {

        var GAME_BUTTON_PHASE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "PHASE"
                GERMAN -> "PHASE"
            }

        var GAME_BUTTON_CREDITS: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "CREDITS"
                GERMAN -> "KREDITS"
            }

        var GAME_BUTTON_CENTER: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Center"
                GERMAN -> "Zentrieren"
            }

        var GAME_BUTTON_DICE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "DICE"
                GERMAN -> "WUERFELN"
            }

        var GAME_BUTTON_CONTINUE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Continue"
                GERMAN -> "Weiter"
            }

        var GAME_BUTTON_STORAGE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Storage"
                GERMAN -> "Lager"
            }


    }
}