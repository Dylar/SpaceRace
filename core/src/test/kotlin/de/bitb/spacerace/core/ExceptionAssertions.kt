package de.bitb.spacerace.core

import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.exceptions.NotCurrentPlayerException


fun GameException.assertDiceException(error: Throwable) =
        when {
            this::class == error::class
            -> true
            this is NotCurrentPlayerException && error is NotCurrentPlayerException
            -> this.player == error.player
            else -> false
        }