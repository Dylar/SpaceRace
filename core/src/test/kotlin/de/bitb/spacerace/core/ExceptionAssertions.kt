package de.bitb.spacerace.core

import de.bitb.spacerace.exceptions.*


fun GameException.assertDiceException(error: Throwable) =
        checkBasic(error) || when {
            else
            -> this::class == error::class
        }

fun GameException.assertMoveException(error: Throwable) =
        checkBasic(error) || when {
            this is NotMovableException && error is NotMovableException
            -> this.player == error.player && this.field == error.field
            else
            -> this::class == error::class
        }

fun GameException.assertNextPhaseException(error: Throwable) =
        checkBasic(error) || when {
            this is NextPhaseException && error is NextPhaseException
            -> this.player == error.player
            else
            -> this::class == error::class
        }

fun GameException.checkBasic(error: Throwable): Boolean =
        when {
            this is NotCurrentPlayerException && error is NotCurrentPlayerException
            -> this.player == error.player
            this is WrongPhaseException && error is WrongPhaseException
            -> this.player == error.player
            else -> false
        }


