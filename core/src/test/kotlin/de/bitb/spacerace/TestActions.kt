package de.bitb.spacerace

import de.bitb.spacerace.TestActions.Action.NEXT_PHASE
import de.bitb.spacerace.model.player.PlayerColor

object TestActions {

    sealed class Action(
            var player: PlayerColor = PlayerColor.NONE
    ) {
        class NEXT_PHASE(player: PlayerColor) : Action(player)
        class DICE(player: PlayerColor) : Action(player)
    }

    fun doAction(
            testEnvironment: SpaceEnvironment,
            action: Action = NEXT_PHASE(PlayerColor.NONE)
    ) {
        when (action) {
            is NEXT_PHASE -> testEnvironment.nextPhase(action.player)
            is Action.DICE ->  testEnvironment.dice(action.player)
        }
    }
}