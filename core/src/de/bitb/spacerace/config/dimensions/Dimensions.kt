package de.bitb.spacerace.config.dimensions

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language.*

object Dimensions {

    var SCREEN_WIDTH = 0f
        get() = Gdx.graphics.width.toFloat()
    var SCREEN_HEIGHT = 0f
        get() = Gdx.graphics.height.toFloat()


    object GameGuiDimensions {

        const val GAME_SIZE_FONT_BIG = 2.5f
        const val GAME_SIZE_FONT_MEDIUM = 2f
        const val GAME_SIZE_FONT_SMALL = 1.5f
        const val GAME_SIZE_FONT_xSMALL = 1f
        const val GAME_SIZE_FONT_TINY = .5f

        const val GAME_BUTTON_WIDTH_DEFAULT = 300f
        const val GAME_BUTTON_HEIGHT_DEFAULT = 100f
        const val GAME_MENU_PADDING = 24f
        const val GAME_MENU_PADDING_SPACE = 12f

        const val GAME_MENU_ITEM_WIDTH_MIN = 6
        const val GAME_MENU_END_ROUND_WIDTH_MIN = 6

        val GAME_LABEL_HEIGHT = SCREEN_HEIGHT / 15f
        val GAME_LABEL_WIDTH = SCREEN_WIDTH / 6f
        val GAME_LABEL_PADDING = GAME_LABEL_WIDTH * 0.2f

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

    object GameDimensions {
        const val GAME_CONNECTIONS_WIDTH = 30
        const val BACKGROUND_STARS_SCALE = 0.15f
        const val FIELD_PADDING_TOO_LARGE = 10f
        const val FIELD_PADDING_XXLARGE = 2.4f
        const val FIELD_PADDING_XLARGE = 1.8f
        const val FIELD_PADDING_LARGE = 1.2f
        const val FIELD_PADDING_MEDIUM = 0.6f
        const val FIELD_PADDING_SMALL = 0.3f

        const val DEFAULT_IMAGE_BORDER = 64
        const val ITEM_BORDER = DEFAULT_IMAGE_BORDER * 0.9f
        const val FIELD_BORDER = DEFAULT_IMAGE_BORDER * 3.5f
        const val PLAYER_BORDER = DEFAULT_IMAGE_BORDER * 1.8f

    }
}