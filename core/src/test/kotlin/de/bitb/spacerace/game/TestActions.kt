package de.bitb.spacerace.game

import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField

object TestActions {

    sealed class Action(
            var player: PlayerColor = PlayerColor.NONE
    ) {

        class NEXT_PHASE(
                player: PlayerColor
        ) : Action(player)

        class DICE(
                player: PlayerColor,
                val setDice: Int = -1
        ) : Action(player)

        class MOVE(
                player: PlayerColor,
                val target: SpaceField
        ) : Action(player)

        fun doAction(
                testEnvironment: SpaceEnvironment
        ) {
            when (this) {
                is NEXT_PHASE -> testEnvironment.nextPhase(player)
                is DICE -> testEnvironment.dice(player, setDice)
                is MOVE -> testEnvironment.move(player, target)
            }

            waitForIt()
        }

    }

    fun waitForIt(time: Long = 100) {
        Thread.sleep(time)
    }
}