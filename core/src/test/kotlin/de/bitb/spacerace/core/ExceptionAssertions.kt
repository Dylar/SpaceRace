package de.bitb.spacerace.core

import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.exceptions.NotCurrentPlayerException


fun GameException.assertDiceException(error: Throwable) =
        when {
            this is NotCurrentPlayerException && error is NotCurrentPlayerException
            -> this.player == error.player
            else
            -> this::class == error::class
        }

fun GameException.assertMoveException(error: Throwable) =
        when {
            else
            -> this::class == error::class
        }

fun GameException.assertNextPhaseException(error: Throwable) =
        when {
            else
            -> this::class == error::class
        }