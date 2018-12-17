package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.ui.Image

abstract class Item(val img: Texture, val text: String) : Image(img) {

    override fun addListener(listener: EventListener?): Boolean {
        if (listeners.size != 0) {
            removeListener(listeners.first())
        }
        return super.addListener(listener)
    }

    fun use(){
        //TODO
    }

}
