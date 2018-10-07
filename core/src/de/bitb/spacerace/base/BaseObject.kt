package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.bitb.spacerace.model.Field

open class BaseObject(val img: Texture) {

    open var field: Field = Field()

    fun move(posX: Float = 0f, posY: Float = 0f) {
        field.posX += posX
        field.posY += posY
    }

    fun moveTo(field:Field){
        moveTo(field.posX, field.posY)
    }

    fun moveTo(posX: Float = 0f, posY: Float = 0f) {
        field.posX = posX
        field.posY = posY
    }

    fun draw(batch: SpriteBatch) {
        batch.draw(img, field.posX, field.posY)
    }

    fun dispose() {
        img.dispose()
    }

    fun collide(pointer: Field): Boolean {
        return field.posX < pointer.posX && field.posX + img.width > pointer.posX
                && field.posY < pointer.posY && field.posY + img.height > pointer.posY
    }
}