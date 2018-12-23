package de.bitb.spacerace.config.strings

import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.PLAYER_AMOUNT
import de.bitb.spacerace.config.enums.Language.*

object Strings {

    object StartGuiStrings {

        var START_BUTTON_START: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Start"
                GERMAN -> "Start"
            }
        var START_BUTTON_PLAYER: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> PLAYER_AMOUNT.toString()
                GERMAN -> PLAYER_AMOUNT.toString()
            }
        var START_BUTTON_LANGUAGE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "English"
                GERMAN -> "Deutsch"
            }
    }

    object GameGuiStrings {

        var GAME_BUTTON_CANCEL: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Cancel"
                GERMAN -> "Abbrechen"
            }

        var GAME_BUTTON_USE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Use"
                GERMAN -> "Benutzen"
            }

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

        var GAME_MENUITEM_TITLE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Items"
                GERMAN -> "Items"
            }

        var GAME_MENU_END_ROUND_TITLE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Round end"
                GERMAN -> "Runden Ende"
            }

        var GAME_MENU_END_ROUND_DETAILS_TITLE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Player: "
                GERMAN -> "Spieler: "
            }

    }
}