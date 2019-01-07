package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.DEBUG_ITEM
import de.bitb.spacerace.config.DEBUG_ITEMS
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.items.equip.EquipItem
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.items.itemtype.MultiDice
import de.bitb.spacerace.model.items.usable.UsableItem
import java.lang.UnsupportedOperationException

data class PlayerItems(val playerColor: PlayerColor = PlayerColor.NONE) {

    var items: MutableMap<ItemState, MutableList<Item>> = HashMap()

    var diceModItems: MutableList<DiceModification> = ArrayList()
    var diceAddItems: MutableList<DiceAddition> = ArrayList()
    var multiDiceItem: MutableList<MultiDice> = ArrayList()

    init {
        ItemState.values().forEach { items[it] = ArrayList() }

        for (i in 1..DEBUG_ITEMS) {
            if (DEBUG_ITEM.isEmpty()) {
                addRandomGift()
            } else {
                val index = (Math.random() * DEBUG_ITEM.size).toInt()
                addItem(DEBUG_ITEM[index].create(playerColor))
            }
        }
    }

    private fun addModification(item: Item) {
        when (item) {
            is DiceModification -> diceModItems.add(item)
            is DiceAddition -> diceAddItems.add(item)
            is MultiDice -> multiDiceItem.add(item)
        }
    }

    private fun removeModification(item: Item) {
        when (item) {
            is DiceModification -> diceModItems.remove(item)
            is DiceAddition -> diceAddItems.remove(item)
            is MultiDice -> multiDiceItem.remove(item)
        }
    }

    fun removeUsedItems() {
        val usedItems = items[ItemState.USED]!!
        usedItems.forEach {
            if (it.charges > 1) {
                it.charges--
                addItem(it)
            }
            removeModification(it)
        }
        usedItems.clear()
    }

    fun getItems(): MutableList<Item> {
        val list = ArrayList<Item>()
        list.addAll(items[ItemState.EQUIPPED]!!)
        list.addAll(items[ItemState.STORAGE]!!)
        return list
    }

    fun getItems(itemType: ItemCollection): List<Item> {
        val list = ArrayList<Item>()
        list.addAll(getItems(items[ItemState.EQUIPPED]!!, itemType))
        list.addAll(getItems(items[ItemState.STORAGE]!!, itemType))
        return list
    }

    fun getSaleableItems(itemType: ItemCollection): MutableList<Item> {
        return getItems(items[ItemState.STORAGE]!!, itemType)
    }

    private fun <T : Item> getItems(items: MutableList<T>, itemType: ItemCollection): MutableList<Item> {
        val list = ArrayList<Item>()
        items.forEach {
            if (it.itemType == itemType) {
                list.add(it)
            }
        }
        return list
    }

    fun addItem(item: Item) {
        item.state = ItemState.STORAGE
        items[ItemState.STORAGE]!!.add(item)
    }

    fun sellItem(item: Item) {
        item.state = ItemState.NONE
        items[ItemState.STORAGE]!!.remove(item)
    }

    fun disposeItem(item: DisposableItem) {
        item.state = ItemState.DISPOSED
        items[ItemState.STORAGE]!!.remove(item)
    }

    fun attachItem(item: DisposableItem) {
        item.state = ItemState.ATTACHED
        items[ItemState.ATTACHED]!!.add(item)
        addModification(item)
    }

    fun detachItems(detachItems: ArrayList<ItemCollection>) {
        items[ItemState.ATTACHED]!!.forEach {
            if (detachItems.contains(it.itemType)) {
                it.state = ItemState.NONE
                removeModification(it)
            }
        }
    }

    fun equipItem(item: EquipItem) {
        item.state = ItemState.EQUIPPED
        items[ItemState.STORAGE]!!.remove(item)
        items[ItemState.EQUIPPED]!!.add(item)
        addModification(item)
    }

    fun unequipItem(item: EquipItem) {
        item.state = ItemState.STORAGE
        items[ItemState.STORAGE]!!.add(item)
        items[ItemState.EQUIPPED]!!.remove(item)
        removeModification(item)
    }

    fun useItem(item: UsableItem) {
        item.state = ItemState.USED
        items[ItemState.STORAGE]!!.remove(item)
        items[ItemState.USED]!!.add(item)
        addModification(item)
    }

    fun removeUsedItem(usableItem: UsableItem) {
        throw UnsupportedOperationException()
    }

    fun addRandomGift(): Item {
        val item = ItemCollection.getRandomItem(playerColor)
        addItem(item)
        return item
    }

}