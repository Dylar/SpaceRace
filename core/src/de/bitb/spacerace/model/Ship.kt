package de.bitb.spacerace.model

import com.badlogic.gdx.math.MathUtils
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.core.TextureCollection

class Ship(val gameColor: GameColors) : BaseObject(TextureCollection.ship1) {

    lateinit var fieldPosition: SpaceField

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

}