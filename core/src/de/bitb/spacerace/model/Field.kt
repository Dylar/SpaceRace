package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

class Field : Vector2(){

    fun setAsInput() {
        x = Gdx.input.x.toFloat()
        y = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
    }

}