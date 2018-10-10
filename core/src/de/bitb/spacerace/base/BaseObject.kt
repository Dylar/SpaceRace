package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image

open class BaseObject(img: Texture) : Image(img) {

    init {
        setBounds(x, y, width, height)
    }

//    fun collide(pointer: Field): Boolean {
//        return field.x < pointer.x && field.x + img.width > pointer.x
//                && field.y < pointer.y && field.y + img.height > pointer.y
//    }
}