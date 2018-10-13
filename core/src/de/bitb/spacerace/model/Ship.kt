package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseObject

class Ship : BaseObject(Texture("banana.png")) {

    lateinit var fieldPosition: SpaceField

    init {
        setBounds(x, y, width * 0.35f, height * 0.35f)
    }


}