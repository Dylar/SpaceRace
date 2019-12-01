package de.bitb.spacerace.grafik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

object TexturePool {

    val bitmapFont = BitmapFont(Gdx.files.internal("spaceranger.fnt")).apply { data.setScale(1.1f) }
    private val texturePool: MutableMap<String, Texture> = mutableMapOf()

    fun getBackground(imagePath: String, width: Float, height: Float) =
            getNinePatch(imagePath, width, height, .25f, .25f, .45f, .45f)

    fun getButton(imagePath: String) =
            getNinePatch(imagePath, 100f, 100f, .40f, .40f, .20f, .20f)

    fun getSmallButton(imagePath: String) =
            getNinePatch(imagePath, 100f, 100f, .33f, .33f, .33f, .33f)

    fun getNinePatch(
            imagePath: String,
            width: Float,
            height: Float,
            leftMod: Float = .33f,
            rightMod: Float = .33f,
            botMod: Float = .33f,
            topMod: Float = .33f
    ): NinePatchDrawable {
        val texture = texturePool[imagePath]
                ?: Texture(Gdx.files.internal(imagePath)).also { texturePool[imagePath] = it }

        return getNinePatch(texture, leftMod, rightMod, botMod, topMod)
    }

    fun getNinePatch(
            texture: Texture,
            leftMod: Float = .33f,
            rightMod: Float = .33f,
            botMod: Float = .33f,
            topMod: Float = .33f
    ): NinePatchDrawable {

        val width = texture.width - 2
        val height = texture.height - 2

        val left = (width * leftMod).toInt()
        val right = (width * rightMod).toInt()
        val bot = (height * botMod).toInt()
        val top = (height * topMod).toInt()

        val textureRegion = TextureRegion(texture, 1, 1, width, height)
        val niePatch = NinePatch(textureRegion, left, right, top, bot)
        return NinePatchDrawable(niePatch)
    }

    fun getDrawable(texture: Texture): TextureRegionDrawable = TextureRegionDrawable(texture)

}

//const val IMAGE_PATH_WINDOW_BACKGROUND: String = "gui/score_0014_window2.9.png"
const val IMAGE_PATH_WINDOW_BACKGROUND: String = "gui/score_0014_window2_edit.9.png"
const val IMAGE_PATH_GUI_BACKGROUND: String = "gui/victory_0013_window2_edit.png"
const val IMAGE_PATH_BUTTON_UP: String = "gui/reg_0000_save_button.9.png"
const val IMAGE_PATH_BUTTON_DOWN: String = "gui/reg_0000_save_button_red.9.png"
