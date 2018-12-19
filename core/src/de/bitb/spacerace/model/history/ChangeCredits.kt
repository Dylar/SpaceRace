package de.bitb.spacerace.model.history

class ChangeCredits(val credits: Int) : Activity() {
    override fun doIt() {
        turn.player.credits += credits
    }
}