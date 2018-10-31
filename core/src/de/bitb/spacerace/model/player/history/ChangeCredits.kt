package de.bitb.spacerace.model.player.history

class ChangeCredits(val credits: Int) : Activity() {
    override fun doIt() {
        turn.ship.credits += credits
    }
}