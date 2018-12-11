package de.bitb.spacerace.config

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.config.enums.Language.*

object Dimensions {

    var SCREEN_WIDTH = 0
        get() = Gdx.graphics.width
    var SCREEN_HEIGHT = 0
        get() = Gdx.graphics.height

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

    object GameDimensions{
        const val GAME_CONNECTIONS_WIDTH = 30



//      TODO  DO ME

        val slotHeight = SCREEN_HEIGHT / 15f
        val slotWidth = SCREEN_WIDTH / 6f
        val singlePadding = slotWidth * 0.2f
        val guiHeight = slotHeight * 2
        val guiWidth = slotWidth * 2 + singlePadding * 2
        val guiPosX = SCREEN_WIDTH - guiWidth
        val fontSize: Float = 2.5f
    }
}