package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

interface MenuItem {
    fun getImage(): Texture
    fun getTintColor(): Color
}