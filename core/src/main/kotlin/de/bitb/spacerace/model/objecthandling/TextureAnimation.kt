package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.core.TextureCollection

class TextureAnimation(
        img: Texture? = null
) : BaseAnimation() {

    var texture: TextureRegion? = null
    private val defaultTexture: TextureRegion by lazy { TextureRegion(TextureCollection.raiderShipMoving2) }

    init {
        img?.also {
            texture = TextureRegion(it)
            region = texture
        }
//        TextureCollection.unknownPlanet
//        region = texture
    }

    override fun actAnimation(gameImage: GameImage, delta: Float) {
        (gameImage.drawable as TextureRegionDrawable).region = texture?.let { it } ?: defaultTexture
    }
}