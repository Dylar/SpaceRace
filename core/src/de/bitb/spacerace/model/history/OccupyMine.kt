package de.bitb.spacerace.model.history

import de.bitb.spacerace.model.space.fields.MineField

class OccupyMine(val mine: MineField) : Activity() {
    override fun doIt() {
        mine.setOwner(turn.player)
    }
}