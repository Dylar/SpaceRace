package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.space.MineField

class OccupyMine(val mine: MineField) : Activity() {
    override fun doIt() {
        mine.setOwner(turn.ship)
    }
}