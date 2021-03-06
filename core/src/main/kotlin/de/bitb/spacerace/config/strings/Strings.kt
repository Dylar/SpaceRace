package de.bitb.spacerace.config.strings

import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language.*

object Strings {

    object StartGuiStrings {

        var START_BUTTON_START: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "New game"
                GERMAN -> "Neues Spiel"
            }

        var START_BUTTON_LOAD: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Load menu"
                GERMAN -> "Lade Menu"
            }

        var START_BUTTON_LANGUAGE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "English"
                GERMAN -> "Deutsch"
            }
    }

    object GameGuiStrings {

        val GAME_TITLE_STORAGE: String = "Storage"
        val GAME_TITLE_SHOP: String = "Shop"

        var GAME_BUTTON_CANCEL: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Cancel"
                GERMAN -> "Abbrechen"
            }

        var GAME_BUTTON_BUY: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Buy"
                GERMAN -> "Kaufen"
            }

        var GAME_BUTTON_SELL: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Sell"
                GERMAN -> "Verkaufen"
            }

        var GAME_BUTTON_USE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Use"
                GERMAN -> "Benutzen"
            }

        val GAME_BUTTON_MODS: String
            get() = when (LANGUAGE) {
                ENGLISH -> "MOD"
                GERMAN -> "MOD"
            }

        var GAME_LABEL_PLAYER_AMOUNT: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Player"
                GERMAN -> "Spieler"
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

        var GAME_MENU_ITEM_DETAILS_TITLE_USED: String = ""
            get() = when (LANGUAGE) {
                ENGLISH ->  "Used"
                GERMAN -> "Benutzt"
            }

        var GAME_MENU_ITEM_DETAILS_TITLE_USABLE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH ->  "Usable"
                GERMAN -> "Benutzbar"
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

        var GAME_SHOP_TITLE: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Shop"
                GERMAN -> "Laden"
            }

        var GAME_ROUND_DETAILS_VICTORIES: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Victories: "
                GERMAN -> "Siege: "
            }

        var GAME_ROUND_DETAILS_CREDITS: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Credits: "
                GERMAN -> "Kredits: "
            }

        var GAME_ROUND_DETAILS_MINES: String = ""
            get() = when (LANGUAGE) {
                ENGLISH -> "Mines: "
                GERMAN -> "Minen: "
            }

    }
}