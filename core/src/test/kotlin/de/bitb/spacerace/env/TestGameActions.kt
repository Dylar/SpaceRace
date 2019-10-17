package de.bitb.spacerace.env

import de.bitb.spacerace.core.assertDiceException
import de.bitb.spacerace.core.assertMoveException
import de.bitb.spacerace.core.assertNextPhaseException
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import de.bitb.spacerace.usecase.game.action.NextPhaseResult

fun TestEnvironment.nextPhase(
        color: PlayerColor = currentPlayerColor,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertNextPhaseException(it) ?: false },
        assertSuccess: ((NextPhaseResult) -> Boolean) = { true }
) = this.apply {
    nextPhaseUseCase.buildUseCaseSingle(color)
            .test()
            .await()
            .assertObserver(error, assertError, assertSuccess)
}

fun TestEnvironment.dice(
        player: PlayerColor = currentPlayerColor,
        setDice: Int = -1,
        error: GameException? = null
) = this.apply {
    diceUsecase.buildUseCaseCompletable(player to setDice)
            .test()
            .await()
            .apply {
                if (error == null) assertComplete()
                else assertError { error.assertDiceException(it) }
            }
    waitForIt()
}

fun TestEnvironment.move(
        player: PlayerColor = currentPlayerColor,
        target: PositionData = leftTopField,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertMoveException(it) ?: false },
        assertSuccess: (MoveResult) -> Boolean = { true }
) = this.apply {
    moveUsecase.buildUseCaseSingle(player to target)
            .test()
            .await()
            .assertObserver(error, assertError, assertSuccess)
    waitForIt()
}