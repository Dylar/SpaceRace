package de.bitb.spacerace.grafik

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

object TexturePool {

    val bitmapFont = BitmapFont(Gdx.files.internal("spaceranger.fnt")).apply { data.setScale(1.1f) }
    private val texturePool: MutableMap<String, Texture> = mutableMapOf()

    fun getBackground(imagePath: String) =
            getNinePatch(imagePath, .25f,.25f,.45f,.45f)

    fun getButton(imagePath: String) =
            getNinePatch(imagePath, .40f,.40f,.20f,.20f)

    fun getNinePatch(
            imagePath: String,
            leftMod: Float,
            rightMod: Float,
            botMod: Float,
            topMod: Float
    ): NinePatchDrawable {
        val texture = texturePool[imagePath]
                ?: Texture(Gdx.files.internal(imagePath)).also { texturePool[imagePath] = it }

        val width = texture.width - 2
        val height = texture.height - 2

        val left = (width * leftMod).toInt()
        val right = (width * rightMod).toInt()
        val bot = (height * botMod).toInt()
        val top = (height * topMod).toInt()

        return NinePatchDrawable(NinePatch(TextureRegion(texture, 1, 1, width, height), left, right, top, bot))
    }
}

const val IMAGE_PATH_GUI_BACKGROUND: String = "gui/score_0014_window2.png"
const val IMAGE_PATH_BUTTON_UP: String = "gui/reg_0000_save_button.9.png"
const val IMAGE_PATH_BUTTON_DOWN: String = "gui/reg_0000_save_button_red.9.png"
