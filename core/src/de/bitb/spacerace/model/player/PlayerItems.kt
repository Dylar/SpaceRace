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
import de.bitb.spacerace.model.items.usable.UsableItem
import java.lang.UnsupportedOperationException

data class PlayerItems(val playerColor: PlayerColor = PlayerColor.NONE) {

    var storageItems: MutableList<Item> = ArrayList()
    var usedItems: MutableList<Item> = ArrayList()
    var equippedItems: MutableList<Item> = ArrayList()
    val attachedItems: MutableList<Item> = ArrayList()

    var diceModItems: MutableList<DiceModification> = ArrayList()
    var diceAddItems: MutableList<DiceAddition> = ArrayList()

    init {
        for (i in 1..DEBUG_ITEMS) {
            if (DEBUG_ITEM == ItemCollection.NONE) {
                addRandomGift()
            } else {
                addItem(DEBUG_ITEM.create(playerColor))
            }
        }
    }

    private fun addModification(item: Item) {
        when (item) {
            is DiceModification -> diceModItems.add(item)
            is DiceAddition -> diceAddItems.add(item)
        }
    }

    private fun removeModification(item: Item) {
        when (item) {
            is DiceModification -> diceModItems.remove(item)
            is DiceAddition -> diceAddItems.remove(item)
        }
    }

    fun removeUsedItems() {
        usedItems.forEach { item ->
            run {
                removeModification(item)
            }
        }
        usedItems.clear()
    }

    fun getItems(): MutableList<Item> {
        val list = ArrayList<Item>()
        list.addAll(equippedItems)
        list.addAll(storageItems)
        return list
    }

    fun getItems(itemType: ItemCollection): List<Item> {
        val list = ArrayList<Item>()
        list.addAll(getItems(equippedItems, itemType))
        list.addAll(getItems(storageItems, itemType))
        return list
    }

    fun getSaleableItems(itemType: ItemCollection): MutableList<Item> {
        return getItems(storageItems, itemType)
    }

    private fun getItems(items: MutableList<Item>, itemType: ItemCollection): MutableList<Item> {
        val list = ArrayList<Item>()
        items.forEach { item ->
            run {
                if (item.itemType == itemType) {
                    list.add(item)
                }
            }
        }
        return list
    }

    fun addItem(item: Item) {
        item.state = ItemState.STORAGE
        storageItems.add(item)
    }

    fun sellItem(item: Item) {
        item.state = ItemState.NONE
        storageItems.remove(item)
    }

    fun disposeItem(item: DisposableItem) {
        item.state = ItemState.DISPOSED
        storageItems.remove(item)
    }

    fun attachItem(item: DisposableItem) {
        item.state = ItemState.ATTACHED
        attachedItems.add(item)
        addModification(item)
    }

    fun equipItem(item: EquipItem) {
        item.state = ItemState.EQUIPPED
        storageItems.remove(item)
        equippedItems.add(item)
        addModification(item)
    }

    fun unequipItem(item: EquipItem) {
        item.state = ItemState.STORAGE
        storageItems.add(item)
        equippedItems.remove(item)
        removeModification(item)
    }

    fun useItem(item: Item) {
        item.state = ItemState.USED
        storageItems.remove(item)
        usedItems.add(item)
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