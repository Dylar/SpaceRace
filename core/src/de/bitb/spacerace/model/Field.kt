package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx

class Field {

    var posX: Float = 0f
    var posY: Float = 0f

    fun setAsInput() {
        posX = Gdx.input.x.toFloat()
        posY = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
    }
}