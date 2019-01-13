package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection

enum class FieldType(val color: Color? = null, val texture: Texture = TextureCollection.unknownPlanet) {
    LOSE(RED, texture = TextureCollection.debrisField),
    WIN(GREEN, texture = TextureCollection.debrisField),
    PLANET(texture = TextureCollection.greenPlanet),
    AMBUSH(texture = TextureCollection.redPlanet),
    MINE(SKY, texture = TextureCollection.minePlanet),
    GIFT(SKY, texture = TextureCollection.debrisField),
    SHOP(texture = TextureCollection.fieldItemShop),
    GOAL(Color.SKY, texture = TextureCollection.goalPlanet),
    TUNNEL(PURPLE, TextureCollection.blackhole),
    RANDOM(GREEN, TextureCollection.blackhole),
    UNKNOWN(texture = TextureCollection.unknownPlanet);

}