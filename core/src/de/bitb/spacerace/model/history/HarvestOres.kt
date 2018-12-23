package de.bitb.spacerace.model.history

class HarvestOres(val credits: Int) : Activity() {
    override fun doIt() {
        turn.player.playerData.credits += credits
    }
}