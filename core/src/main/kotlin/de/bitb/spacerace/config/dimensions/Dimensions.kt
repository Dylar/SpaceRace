package de.bitb.spacerace.config.dimensions

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.config.IS_TEST
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language.ENGLISH
import de.bitb.spacerace.config.enums.Language.GERMAN


object Dimensions {

    val SCREEN_WIDTH
        get() = if (IS_TEST) 200f else Gdx.graphics.width.toFloat()
    val SCREEN_HEIGHT
        get() = if (IS_TEST) 100f else Gdx.graphics.height.toFloat()

    val SCREEN_WIDTH_HALF
        get() = SCREEN_WIDTH / 2
    val SCREEN_HEIGHT_HALF
        get() = SCREEN_HEIGHT / 2

    const val ONE_EIGHTY_DEGREE = 180.0
    const val ONE_TWENTY_DEGREE = 120.0
    const val NINETY_DEGREE = 90.0

    object GameGuiDimensions {

        val GAME_BUTTON_WIDTH_DEFAULT
            get() = SCREEN_WIDTH / 6
        val GAME_BUTTON_HEIGHT_DEFAULT
            get() = SCREEN_HEIGHT / 8
        val GAME_LABEL_WIDTH_DEFAULT
            get() = SCREEN_HEIGHT / 4 * 0.8f
        val GAME_LABEL_HEIGHT_DEFAULT
            get() = SCREEN_HEIGHT / 20 * 0.8f


        const val GAME_SIZE_FONT_BIG = 2.5f
        const val GAME_SIZE_FONT_MEDIUM = 2f
        const val GAME_SIZE_FONT_SMALL = 1.5f
        const val GAME_SIZE_FONT_xSMALL = 1f
        const val GAME_SIZE_FONT_TINY = .5f

        const val GAME_GUI_PADDING = 24f
        const val GAME_GUI_PADDING_SPACE = 12f

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

        const val GAME_CONNECTIONS_WIDTH = 20f
        const val BACKGROUND_STARS_SCALE = 0.15f
        const val FIELD_PADDING_TOO_LARGE = 10f
        const val FIELD_PADDING_XXLARGE = 2.4f
        const val FIELD_PADDING_XLARGE = 1.8f
        const val FIELD_PADDING_LARGE = 1.2f
        const val FIELD_PADDING_MEDIUM = 0.6f
        const val FIELD_PADDING_SMALL = 0.3f

        const val TINY_BORDER = 16
        const val SMALL_BORDER = TINY_BORDER * 2
        const val DEFAULT_BORDER = SMALL_BORDER * 2
        const val ITEM_BORDER = DEFAULT_BORDER * 0.9f
        const val FIELD_BORDER = DEFAULT_BORDER * 3.5f
        const val PLAYER_BORDER = DEFAULT_BORDER * 1.8f

    }
}