package de.bitb.spacerace.exceptions

import de.bitb.spacerace.model.player.PlayerColor

sealed class GameException(
        message: String
) : Throwable(message)

class NotCurrentPlayerException(
        val player: PlayerColor
) : GameException("$player is not current player")
