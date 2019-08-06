package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

class TextureAnimation(
        img: Texture? = null
) : BaseAnimation() {

    var texture: TextureRegion? = null

    init {
        img?.also {
            texture = TextureRegion(it)
            region = texture
        }
//        TextureCollection.unknownPlanet
//        region = texture
    }

    override fun actAnimation(gameImage: GameImage, delta: Float) {
        texture?.also {
            (gameImage.drawable as TextureRegionDrawable).region = it
        }
    }
}