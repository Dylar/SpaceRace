package de.bitb.spacerace.env

import de.bitb.spacerace.config.DEBUG_GIFT_ITEMS
import de.bitb.spacerace.config.DEBUG_PLAYER_ITEMS
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.assertCurrentPhase
import de.bitb.spacerace.core.assertSameField
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameResult

fun TestEnvironment.setGiftFieldItems(createItems: () -> List<ItemInfo>) =
        this.also { DEBUG_GIFT_ITEMS = createItems() }

fun TestEnvironment.setPlayerItems(createItems: () -> List<ItemInfo>) =
        this.also { DEBUG_PLAYER_ITEMS = createItems() }

private fun TestEnvironment.initMap(
        mapToLoad: MapData = createTestMap()
) {
    mapDataSource.insertDBMaps(mapToLoad)

    leftBottomField = mapToLoad.fields[0].gamePosition
    leftTopField = mapToLoad.fields[1].gamePosition
    centerBottomField = mapToLoad.fields[2].gamePosition
    centerTopField = mapToLoad.fields[3].gamePosition
    leftSideField = mapToLoad.fields[4].gamePosition
    circleStep1Field = mapToLoad.fields[5].gamePosition
    circleStep2Field = mapToLoad.fields[6].gamePosition
    bridgeField = mapToLoad.fields[7].gamePosition
}

fun TestEnvironment.initGame(
        vararg playerColor: PlayerColor = DEFAULT_TEST_PLAYER.toTypedArray(),
        map: MapData = createTestMap(),
        winAmount: Long = 1,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { false },
        assertSuccess: (LoadGameResult) -> Boolean = { true }
) = this.apply {
    WIN_AMOUNT = winAmount

    testGame = TestGame()
    testGame.initGame()

    TestGame.testComponent.inject(this)

    initMap(map)
    val config = LoadGameConfig(playerColor.toList(), map.name)
    loadNewGameUsecase.buildUseCaseSingle(config)
            .test()
            .await()
            .assertObserver(error, assertError, assertSuccess)

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
        moveTo: PositionData = leftTopField
) = this.apply {
    setToMovePhase()

    move(target = moveTo)
    nextPhase()

    assertCurrentPhase(Phase.MAIN2)
}

fun TestEnvironment.changePlayerTo(
        player: PlayerColor,
        moveTo: PositionData = leftTopField
) = this.apply {
    while (player != currentPlayerColor) {
        setToMain2Phase(moveTo = moveTo)
        nextPhase()
    }
}

fun TestEnvironment.endTurn(
        moveTo: PositionData = leftSideField
) = this.apply {
    if (currentPlayer.phase == Phase.MAIN1) setToMain2Phase(moveTo)
    if (currentPlayer.phase == Phase.MAIN2) nextPhase()
}

fun TestEnvironment.endRound(
        moveTo: PositionData = leftSideField
) = this.apply {
    endTurn(moveTo)

    changePlayerTo(
            player = playerController.players.first(),
            moveTo = moveTo)

    startNewRoundUsecase.buildUseCaseSingle().test().await().assertComplete()
}

fun TestEnvironment.moveToGoal(
) = this.apply {
    setToMovePhase()
    move(target = centerBottomField)
    assertSameField(getPlayerPosition(), centerBottomField)
}