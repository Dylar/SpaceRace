package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseObject

class SpaceField(var id: Int = -1) : BaseObject(Texture("asteroid.png")) {
    val connections: MutableList<SpaceField> = ArrayList()

    init {
        setBounds(x, y, width * 0.5f, height * 0.5f)
        color = Color.YELLOW
    }

    fun hasConnectionTo(spaceField: SpaceField): Boolean {
        return connections.contains(spaceField)
    }
}