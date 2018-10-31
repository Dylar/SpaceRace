package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.player.history.History
import de.bitb.spacerace.model.space.SpaceField
import de.bitb.spacerace.ui.MenuItem

class Ship(val gameColor: GameColors = GameColors.NONE, img: Texture = TextureCollection.ship1) : BaseObject(img), MenuItem {

    lateinit var history: History
    lateinit var fieldPosition: SpaceField
    lateinit var fieldGroup: SpaceField

    var credits = 0
    var items = ArrayList<Item>()

    override fun getImage(): Texture {
        return img
    }

    init {
        setBounds(x, y, width * 1.8f, height * 1.8f)
//        setOrigin(width / 2, height / 2)
//        addRandomGift()
//        addRandomGift()
//        addRandomGift()
//        addRandomGift()
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