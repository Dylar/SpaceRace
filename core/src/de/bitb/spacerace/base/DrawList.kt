package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.bitb.spacerace.model.Field

class DrawList : ArrayList<BaseObject>() {
    fun draw(batch: SpriteBatch) {
        for (baseObject in this) {
            baseObject.draw(batch)
        }
    }

    fun move(posX: Float, posY: Float) {

    }

    fun collide(field: Field): Field {
         for (baseObject in this) {
             if(baseObject.collide(field)){
                 return baseObject.field
             }
        }
        return field
    }

}