package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx

class Field{
     fun input(inX: Float, inY: Float) {
          posX = inX
          posY = Gdx.graphics.height.toFloat() - inY
     }

     var posX:Float = 0f
     var posY:Float = 0f
}