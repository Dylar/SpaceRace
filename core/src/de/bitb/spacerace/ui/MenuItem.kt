package de.bitb.spacerace.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture

interface MenuItem {
    fun getImage(): Texture
    fun getTintColor(): Color
}