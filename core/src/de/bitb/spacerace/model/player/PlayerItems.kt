package de.bitb.spacerace.model.player

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

    fun removeUsedItems() {
        usedItems.forEach { item ->
            run {
                diceModItems.remove(item as? DiceModification)
                diceAddItems.remove(item as? DiceAddition)
            }
        }
        usedItems.clear()
    }

    fun getItems(itemType: ItemCollection): List<Item> {
        val list = ArrayList<Item>()
        list.addAll(getItems(storageItems, itemType))
        list.addAll(getItems(equippedItems, itemType))
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
    }

    fun equipItem(item: EquipItem) {
        item.state = ItemState.EQUIPPED
        storageItems.remove(item)
        equippedItems.add(item)
    }

    fun unequipItem(item: EquipItem) {
        item.state = ItemState.STORAGE
        storageItems.add(item)
        equippedItems.remove(item)
    }

    //TODO remove modifiaction list
    fun useItem(item: Item) {
        item.state = ItemState.USED
        storageItems.remove(item)
        usedItems.add(item)
    }

    fun removeUsedItem(usableItem: UsableItem) {
        throw UnsupportedOperationException()
    }

}