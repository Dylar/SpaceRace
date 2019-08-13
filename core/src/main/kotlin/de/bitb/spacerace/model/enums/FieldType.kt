package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.space.fields.FieldAnimation

enum class FieldType(
        val color: Color? = null
) {
    LOSE(RED),
    WIN(GREEN),
    PLANET(),
    AMBUSH(),
    MINE(SKY),
    GIFT(SKY),
    SHOP(),
    GOAL(SKY),
    TUNNEL(PURPLE),
    RANDOM(GREEN),
    UNKNOWN();

    val texture: Texture
        get() = when (this) {
            LOSE -> TextureCollection.debrisField
            WIN -> TextureCollection.debrisField
            PLANET -> TextureCollection.greenPlanet
            AMBUSH -> TextureCollection.redPlanet
            MINE -> TextureCollection.minePlanet
            GIFT -> TextureCollection.debrisField
            SHOP -> TextureCollection.fieldItemShop
            GOAL -> TextureCollection.goalPlanet
            TUNNEL -> TextureCollection.blackhole
            RANDOM -> TextureCollection.blackhole
            UNKNOWN -> TextureCollection.unknownPlanet
        }

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