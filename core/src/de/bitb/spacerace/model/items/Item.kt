package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.ui.Image

abstract class Item(val img: Texture, val text: String) {

    fun getDisplayImage(): Image {
        return object : Image(img) {}
    }

    fun use(){
        //TODO
    }

}
