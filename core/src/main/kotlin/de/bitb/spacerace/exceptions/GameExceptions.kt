package de.bitb.spacerace.exceptions

import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField

sealed class GameException(
        message: String
) : Throwable(message)

class NotCurrentPlayerException(
        val player: PlayerColor
) : GameException("$player is not current player")

class PlayerNotInPhaseException(
        val player: PlayerColor,
        val phase: Phase
) : GameException("$player not in phase $phase")


sealed class NotMovableException(
        val player: PlayerColor,
        val field: SpaceField,
        reason: String
) : GameException("$player can not move to $field - $reason")

class FieldsNotConnectedException(
        player: PlayerColor,
        field: SpaceField
) : NotMovableException(player, field, "not connected")

class NoStepsLeftException(
        player: PlayerColor,
        field: SpaceField
) : NotMovableException(player, field, "no steps left")


sealed class NextPhaseException(
        val player: PlayerColor,
        reason: String
) : GameException("$player cannot change phase - $reason")

class DiceFirstException(
        player: PlayerColor
) : NextPhaseException(player, "Need to dice first")

class MoveEveryStepException(
        player: PlayerColor
) : NextPhaseException(player, "Move every step")

