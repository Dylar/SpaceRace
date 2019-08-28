package de.bitb.spacerace.exceptions

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField

sealed class GameException(
        message: String
) : Throwable(message)

class NotCurrentPlayerException(
        val player: PlayerColor
) : GameException("$player is not current player")

class NotMovableException(
        val player: PlayerColor,
        val field: SpaceField
) : GameException("$player can not move to $field")

