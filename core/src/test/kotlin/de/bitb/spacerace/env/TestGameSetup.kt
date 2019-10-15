package de.bitb.spacerace.env

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.assertCurrentPhase
import de.bitb.spacerace.core.assertSameField
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameResult

fun TestEnvironment.initGame(
        vararg playerColor: PlayerColor = DEFAULT_TEST_PLAYER.toTypedArray(),
        mapToLoad: MapData = createTestMap(),
        winAmount: Long = 1,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { false },
        assertSuccess: (LoadGameResult) -> Boolean = { true }
) = this.apply {
    WIN_AMOUNT = winAmount

    testGame = TestGame()
    testGame.initGame()

    TestGame.testComponent.inject(this)

    leftBottomField = mapToLoad.fields[0].gamePosition
    leftTopField = mapToLoad.fields[1].gamePosition
    centerBottomField = mapToLoad.fields[2].gamePosition
    centerTopField = mapToLoad.fields[3].gamePosition

    val config = LoadGameConfig(playerColor.toList(), mapToLoad)
    loadNewGameUsecase.buildUseCaseSingle(config)
            .test()
            .await()
            .apply { assertObserver(error, assertError, assertSuccess) }

    winnerObserver = gameController
            .observeWinnerUsecase
            .buildUseCaseObservable(winAmount)
            .test()

    gameController.initPhaseObserver() //Only phase observer -> so winner is as test observer
    playerController.initObserver()

    waitForIt()
}

fun TestEnvironment.setToMovePhase(
        setDice: Int = 1
) = this.apply {
    dice(setDice = -setDice)
    nextPhase()

    assertCurrentPhase(Phase.MOVE)
}

fun TestEnvironment.setToMain2Phase(
) = this.apply {
    setToMovePhase()

    move()
    nextPhase()

    assertCurrentPhase(Phase.MAIN2)
}

fun TestEnvironment.changePlayerTo(
        player: PlayerColor
) = this.apply {
    while (player != currentPlayerColor) {
        setToMain2Phase()
        nextPhase()
    }
}

fun TestEnvironment.moveToGoal(
) = this.apply {
    setToMovePhase()
    move(target = centerBottomField)
    assertSameField(getPlayerPosition(), centerBottomField)
}