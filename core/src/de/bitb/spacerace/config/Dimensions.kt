package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.Language.*

object Dimensions {

    object GameGuiDimensions {

        const val GAME_BUTTON_WIDTH_DEFAULT = 300f
        const val GAME_BUTTON_HEIGHT_DEFAULT = 100f

        var GAME_BUTTON_WIDTH_CONTINUE: Float = 0f
            get() = when (LANGUAGE) {
                ENGLISH -> 400f
                GERMAN -> 300f
            }

        var GAME_BUTTON_HEIGHT_CONTINUE: Float = 0f
            get() = when (LANGUAGE) {
                ENGLISH -> GAME_BUTTON_HEIGHT_DEFAULT
                GERMAN -> GAME_BUTTON_HEIGHT_DEFAULT
            }

        var GAME_BUTTON_WIDTH_DICE: Float = 0f
            get() = when (LANGUAGE) {
                ENGLISH -> 300f
                GERMAN -> 450f
            }

        var GAME_BUTTON_HEIGHT_DICE: Float = 0f
            get() = when (LANGUAGE) {
                ENGLISH -> GAME_BUTTON_HEIGHT_DEFAULT
                GERMAN -> GAME_BUTTON_HEIGHT_DEFAULT
            }


    }
}