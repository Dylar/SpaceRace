package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import de.bitb.spacerace.base.BaseObject

class Button : BaseObject(Texture("badlogic.jpg")) {

    val skin: Skin = Skin(Gdx.files.internal("uiSkin.json"))

    init {
        setScale(0.5f)
    }
}