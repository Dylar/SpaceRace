package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerController(val space: BaseSpace) {

    var firstPlayer: Player = Player()
    var currentPlayer: Player = firstPlayer
        get() = if (players.isEmpty()) firstPlayer else players[players.size - 1]
    var players: MutableList<Player> = ArrayList()

    var diced: Boolean = false
    var diceResult: Int = 0
    var steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField()
        get() = steps[steps.size - 2]

    init {
        space.history.nextRound(currentPlayer)
    }

    fun moveTo(spaceField: SpaceField) {
        val ship = currentPlayer
        if (space.fieldController.hasConnectionTo(ship.fieldPosition, spaceField) && space.phaseController.phase.isMoving()) {
            val sameField = steps.size > 1 && previousStep == spaceField
            if (steps.size <= diceResult || sameField) {
                if (sameField) {
                    steps.removeAt(steps.size - 1)
                } else {
                    steps.add(spaceField)
                }

                ship.fieldPosition = spaceField
                ship.moveTo(spaceField)
                Logger.println("Player Field: ${ship.fieldPosition.id}, ${ship.fieldPosition.fieldType.name}")

            }
        }
    }

    fun dice(maxResult: Int = 6, anyway: Boolean = false) {
        if (anyway || diceResult - steps.size <= 0 && !diced && space.phaseController.phase.isMain1()) {
            diced = if (anyway) diced else true
            steps.add(currentPlayer.fieldPosition)
            diceResult += (Math.random() * maxResult).toInt() + 1
            Logger.println("DiceResult: $diceResult")
        }
    }

    fun stepsLeft(): Int {
        return diceResult - (steps.size - 1)
    }

    fun isRoundEnd(): Boolean {
        return firstPlayer != currentPlayer
    }

}