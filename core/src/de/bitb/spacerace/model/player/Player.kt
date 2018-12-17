package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.DEBUG_ITEMS
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.player.history.History
import de.bitb.spacerace.model.space.SpaceField

class Player(val playerColor: PlayerColor = PlayerColor.NONE, img: Texture = TextureCollection.ship1) : BaseObject(img) {

    lateinit var history: History
    lateinit var fieldPosition: SpaceField
    lateinit var fieldGroup: SpaceField

    var credits = 0
    var items = ArrayList<Item>()

    init {
        touchable = Touchable.disabled
        setBounds(x, y, width * 1.8f, height * 1.8f)

        for (i in 0..DEBUG_ITEMS) {
            addRandomGift()
        }
    }

    override fun getDisplayImage(): Image {
        val image = super.getDisplayImage()
        image.color = playerColor.color
        return image
    }

    fun addRandomWin(): Int {
        val win = (Math.random() * 1000).toInt() + 1
        credits += win
        Logger.println("Won: $win")
        return win
    }

    fun substractRandomWin(): Int {
        val lose = (Math.random() * 500).toInt() + 1
        credits -= lose
        Logger.println("Lost: $lose")
        return lose
    }

    fun addRandomGift(): Item {
        Logger.println("U got a gift")
        val item = ItemCollection.getRandomItem()
        items.add(item)
        return item
    }

}