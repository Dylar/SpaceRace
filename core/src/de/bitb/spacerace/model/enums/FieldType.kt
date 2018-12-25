package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection

enum class FieldType(val color: Color? = null, val texture: Texture = TextureCollection.grayField) {
    WIN(GREEN),
    LOSE(RED),
    SHOP(GRAY),
    GIFT(YELLOW),
    AMBUSH(BROWN),
    MINE(BLUE),
    RANDOM(BROWN, TextureCollection.blackhole),
    UNKNOWN(texture = TextureCollection.blackhole);

}