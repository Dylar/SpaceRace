package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.ui.base.MenuItem

class Item(val img: Texture) : Image(img), MenuItem {

    override fun addListener(listener: EventListener?): Boolean {
        if (listeners.size != 0) {
            removeListener(listeners.first())
        }
        return super.addListener(listener)
    }

    override fun getImage(): Texture {
        return img
    }

    override fun getTintColor(): Color {
        return color
    }

}
