package de.bitb.spacerace.exceptions

import de.bitb.spacerace.model.player.PlayerColor

class NotCurrentPlayerException(val player: PlayerColor) : Throwable("$player is not current player")
