package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.core.TextureCollection

class Ship(val gameColor: GameColors, val img: Texture = TextureCollection.ship1) : BaseObject(img) {

    lateinit var fieldPosition: SpaceField

    var credits = 0

    init {
        setBounds(x, y, width * 0.55f, height * 0.55f)
    }

    fun accelerateForward(speed: Float) {
        setAccelerationAS(getRotation(), speed)
    }

    // set acceleration from angle and speed
    fun setAccelerationAS(angleDeg: Float, speed: Float) {
        x = speed * MathUtils.cosDeg(angleDeg)
        y = speed * MathUtils.sinDeg(angleDeg)
    }

    override fun act(delta: Float) {
        super.act(delta)
//        accelerateForward(10f)
    }

    fun addRandomWin() {
        credits += (Math.random() * 1000).toInt() + 1
        Logger.println("Credits: $credits")
    }

    fun substractRandomWin() {
        credits -= (Math.random() * 500).toInt() + 1
        Logger.println("Credits: $credits")
    }

    fun addRandomGift() {
        Logger.println("U got a gift")
    }

}