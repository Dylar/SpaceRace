package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.space.SpaceField
import de.bitb.spacerace.ui.MenuItem

class Ship(val gameColor: GameColors, img: Texture = TextureCollection.ship1) : BaseObject(img), MenuItem {

    lateinit var fieldPosition: SpaceField
    lateinit var fieldGroup: SpaceField

    var credits = 0
    var items = ArrayList<Item>()

    override fun getImage(): Texture {
        return img
    }

    init {
        setBounds(x, y, width * 0.55f, height * 0.55f)
//        setOrigin(width / 2, height / 2)
        addRandomGift()
//        addRandomGift()
//        addRandomGift()
//        addRandomGift()
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
        items.add(ItemCollection.getRandomItem())
    }

}