package de.bitb.spacerace.model.player.history

class HarvestOres(val credits: Int) : Activity() {
    override fun doIt() {
        turn.ship.credits += credits
    }
}