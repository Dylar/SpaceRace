package de.bitb.spacerace.model.player

import de.bitb.spacerace.database.items.DiceAddition
import de.bitb.spacerace.database.items.DiceModification
import de.bitb.spacerace.database.items.MultiDice
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.items.disposable.DisposableItemGraphic
import de.bitb.spacerace.model.items.equip.EquipItemGraphic
import de.bitb.spacerace.model.items.usable.UsableItemGraphic

data class PlayerItems(val playerColor: PlayerColor) {

    var items: MutableMap<ItemState, MutableList<ItemGraphic>> = HashMap()

    var diceModItems: MutableList<DiceModification> = ArrayList()
    var diceAddItems: MutableList<DiceAddition> = ArrayList()
    var multiDiceItem: MutableList<MultiDice> = ArrayList()

    init {
        ItemState.values().forEach { items[it] = ArrayList() }

        //TODO add debug items
//        for (i in 1..DEBUG_ITEMS) {
//            if (DEBUG_ITEM.isEmpty()) {
//                addRandomGift()
//            } else {
//                if (DEBUG_ITEM[0] == ItemType.NONE) {
//                    ItemType.values().forEach {
//                        if (it != ItemType.NONE) addItem(it.create(playerColor))
//                    }
//                } else {
//                    val index = (Math.random() * DEBUG_ITEM.size).toInt()
//                    addItem(DEBUG_ITEM[index].create(playerColor))
//                }
//            }
//        }
    }

    private fun addModification(itemGraphic: ItemGraphic) {
        when (itemGraphic) {
            is DiceModification -> diceModItems.add(itemGraphic)
            is DiceAddition -> diceAddItems.add(itemGraphic)
            is MultiDice -> multiDiceItem.add(itemGraphic)
        }
    }

    private fun removeModification(itemGraphic: ItemGraphic) {
        when (itemGraphic) {
            is DiceModification -> diceModItems.remove(itemGraphic)
            is DiceAddition -> diceAddItems.remove(itemGraphic)
            is MultiDice -> multiDiceItem.remove(itemGraphic)
        }
    }

    fun getItems(itemInfo: ItemInfo): List<ItemGraphic> {
        val list = ArrayList<ItemGraphic>()
        list.addAll(getItems(items[ItemState.EQUIPPED]!!, itemInfo))
        list.addAll(getItems(items[ItemState.STORAGE]!!, itemInfo))
        return list
    }

    private fun <T : ItemGraphic> getItems(items: MutableList<T>, itemInfo: ItemInfo): MutableList<ItemGraphic> {
        val list = ArrayList<ItemGraphic>()
        items.forEach {
            if (it.itemInfo == itemInfo) {
                list.add(it)
            }
        }
        return list
    }

    fun addItem(itemGraphic: ItemGraphic) {
        itemGraphic.state = ItemState.STORAGE
        items[ItemState.STORAGE]!!.add(itemGraphic)
    }

    fun disposeItem(item: DisposableItemGraphic) {
        item.state = ItemState.DISPOSED
        items[ItemState.STORAGE]!!.remove(item)
    }

    fun attachItem(item: DisposableItemGraphic) {
        item.state = ItemState.ATTACHED
        items[ItemState.ATTACHED]!!.add(item)
        addModification(item)
    }

    fun detachItems(detachItems: ArrayList<ItemInfo>) {
        items[ItemState.ATTACHED]!!.forEach {
            if (detachItems.contains(it.itemInfo)) {
                it.state = ItemState.NONE
                removeModification(it)
                it.itemImage.remove()
            }
        }
    }

    fun equipItem(item: EquipItemGraphic) {
        item.state = ItemState.EQUIPPED
        items[ItemState.STORAGE]!!.remove(item)
        items[ItemState.EQUIPPED]!!.add(item)
        addModification(item)
    }

    fun unequipItem(item: EquipItemGraphic) {
        item.state = ItemState.STORAGE
        items[ItemState.STORAGE]!!.add(item)
        items[ItemState.EQUIPPED]!!.remove(item)
        removeModification(item)
    }

    fun useItem(item: UsableItemGraphic) {
        item.state = ItemState.USED
        items[ItemState.STORAGE]!!.remove(item)
        items[ItemState.USED]!!.add(item)
        addModification(item)
    }

    fun removeUsedItem(usableItem: UsableItemGraphic) {
        throw UnsupportedOperationException()
    }

}