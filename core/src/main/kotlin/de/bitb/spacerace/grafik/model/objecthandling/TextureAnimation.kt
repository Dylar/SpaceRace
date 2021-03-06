package de.bitb.spacerace.grafik.model.objecthandling

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.grafik.TextureCollection

class TextureAnimation(
        private val img: Texture? = null
) : BaseAnimation() {

    var texture: TextureRegion? = null
    private val defaultTexture: TextureRegion by lazy { TextureRegion(TextureCollection.unknownPlanet) }

    override fun getDefaultTexture(): Texture? = img

//What is happening :D
    init {
        img?.also {
            texture = TextureRegion(it)
            region = texture
        }
//        TextureCollection.unknownPlanet
//        region = texture
    }

    override fun actAnimation(gameImage: GameImage, delta: Float) {
//        (gameImage.drawable as TextureRegionDrawable).region = TextureRegion(TextureCollection.raiderShipMoving2)
        (gameImage.drawable as TextureRegionDrawable).region = texture?.let { it } ?: defaultTexture
    }
}