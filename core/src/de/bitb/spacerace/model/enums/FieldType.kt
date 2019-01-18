package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.space.fields.FieldAnimation

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

    fun getAnimation(): BaseAnimation {
        return when (this) {
            LOSE -> FieldAnimation(this)
//            WIN -> TODO()
//            PLANET -> TODO()
//            AMBUSH -> TODO()
//            MINE -> TODO()
//            GIFT -> TODO()
//            SHOP -> TODO()
//            GOAL -> TODO()
//            TUNNEL -> TODO()
//            RANDOM -> TODO()
//            UNKNOWN -> TODO()
            else -> {
                FieldAnimation(this)
            }
        }
    }

}