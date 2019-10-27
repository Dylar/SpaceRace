package de.bitb.spacerace.grafik.model.space.fields

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.BaseAnimation
import de.bitb.spacerace.grafik.model.objecthandling.GameImage

class FieldAnimation(
        val fieldType: FieldType
) : BaseAnimation() {

//    var fieldTexture =
//
//    init {
//        region = fieldTexture
//    }

    override fun actAnimation(gameImage: GameImage, delta: Float) {
        (gameImage.drawable as TextureRegionDrawable).region = TextureRegion(fieldType.texture)
    }
}