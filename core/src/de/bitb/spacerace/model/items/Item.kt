package de.bitb.spacerace.model.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.ui.MenuItem

class Item(val img: Texture) : Image(img), MenuItem {
    override fun getImage(): Texture {
        return img
    }

}
