package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection

enum class FieldType(val color: Color? = null, val texture: Texture = TextureCollection.grayField) {
    LOSE(RED),
    WIN(GREEN),
    AMBUSH(BROWN),
    MINE(BLUE),
    GIFT(YELLOW),
    SHOP(texture = TextureCollection.fieldItemShop),
    GOAL(PINK),
    TUNNEL(PURPLE, TextureCollection.blackhole),
    RANDOM(GREEN, TextureCollection.blackhole),
    UNKNOWN(texture = TextureCollection.blackhole);

}