package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection

enum class FieldType(val texture: Texture) {
    WIN(TextureCollection.greenField),
    LOSE(TextureCollection.redField),
    SHOP(TextureCollection.grayField),
    GIFT(TextureCollection.yellowField),
    AMBUSH(TextureCollection.brownField),
    MINE(TextureCollection.blueField),
    RANDOM(TextureCollection.brownField), 
    UNKNOWN(TextureCollection.blackhole);
}