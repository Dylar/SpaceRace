package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.items.Item

class AddItem(val item: Item) : Activity() {
    override fun doIt() {
        turn.player.items.add(item)
    }
}