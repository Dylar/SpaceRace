package de.bitb.spacerace.env

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.*
import de.bitb.spacerace.core.*
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCreator
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseResult
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.game.getter.GetFieldUsecase
import de.bitb.spacerace.usecase.game.getter.GetMapUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameInfo
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import javax.inject.Inject

val TEST_PLAYER_1 = PlayerColor.ORANGE
val TEST_PLAYER_2 = PlayerColor.GREEN
val TEST_PLAYER_3 = PlayerColor.PINK
val DEFAULT_TEST_PLAYER = mutableListOf(TEST_PLAYER_1, TEST_PLAYER_2)

class SpaceEnvironment {

    @Inject
    lateinit var graphicController: GraphicController

    @Inject
    lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase
    @Inject
    lateinit var diceUsecase: DiceUsecase
    @Inject
    lateinit var moveUsecase: MoveUsecase

    @Inject
    lateinit var getPlayerUsecase: GetPlayerUsecase
    @Inject
    lateinit var getFieldUsecase: GetFieldUsecase
    @Inject
    lateinit var getMapUsecase: GetMapUsecase
    @Inject
    lateinit var playerController: PlayerController
    @Inject
    lateinit var fieldController: FieldController

    lateinit var winnerObserver: TestObserver<PlayerData>

    lateinit var testGame: TestGame
    lateinit var testMap: SpaceMap

    val setup = Setup()

    val currentPlayer: PlayerData
        get() = playerController.currentPlayerData
    val currentPlayerColor: PlayerColor
        get() = playerController.currentColor
    val currentPhase: Phase
        get() = currentPlayer.phase
    val currentPosition: SpaceField
        get() = getPlayerField(currentPlayerColor)

    val centerBottomField: SpaceField
        get() = getField(0)
    val leftBottomField: SpaceField
        get() = getField(1)
    val leftTopField: SpaceField
        get() = getField(4)
    val centerTopField: SpaceField
        get() = getField(3)

//    val leftBottomField: SpaceField
//        get() = getField(1)
//    val leftTopField: SpaceField
//
//    ███████╗███████╗████████╗     ██████╗  █████╗ ███╗   ███╗███████╗
//    ██╔════╝██╔════╝╚══██╔══╝    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝
//    ███████╗█████╗     ██║       ██║  ███╗███████║██╔████╔██║█████╗
//    ╚════██║██╔══╝     ██║       ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝
//    ███████║███████╗   ██║       ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗
//    ╚══════╝╚══════╝   ╚═╝        ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝
//

    fun initGame(
            vararg playerColor: PlayerColor = DEFAULT_TEST_PLAYER.toTypedArray(),
            mapToLoad: MapCreator = MapCreator.TEST_MAP,
            winAmount: Long = 1,
            error: GameException? = null,
            assertError: (Throwable) -> Boolean = { false },
            assertSuccess: (LoadGameInfo) -> Boolean = { true }
    ) {
        WIN_AMOUNT = winAmount

        testGame = TestGame()
        testGame.initGame()

        TestGame.testComponent.inject(this)

        testMap = mapToLoad.createMap()
        val mapData = testGame.initMap(playerColor.toList(), testMap)
        val config = LoadGameConfig(map = mapData)
        loadGameUsecase.buildUseCaseSingle(config)
                .test()
                .await()
                .apply {
                    val setAndAssertSuccess: (LoadGameInfo) -> Boolean = {
                        assertSuccess(it)
                    }
                    assertObserver(error, assertError, setAndAssertSuccess)
                }

//        testGame.initGameObserver()
        testGame.initPhaseObserver() //Only phase observer -> so winner is as test observer
        winnerObserver = testGame
                .observeWinnerUsecase
                .buildUseCaseObservable(winAmount)
                .test()

        waitForIt()
    }

    fun setToMovePhase(setDice: Int = -1) {
        dice(setDice = setDice)
        nextPhase()

        assertCurrentPhase(Phase.MOVE)
    }

    fun setToMain2Phase() {
        setToMovePhase()

        move()
        nextPhase()

        assertCurrentPhase(Phase.MAIN2)
    }

    fun changePlayerTo(player: PlayerColor) {
        if (player != currentPlayerColor) {
            setToMain2Phase()
            nextPhase()
            changePlayerTo(player)
        }
    }

    fun moveToGoal() {
        setToMovePhase()
        move(target = centerBottomField)
        assertSameField(getPlayerField(), centerBottomField)
    }

    //
//    ██████╗ ███████╗████████╗████████╗███████╗██████╗
//    ██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗
//    ██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝
//    ██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗
//    ╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║
//    ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝
//
    fun getPlayerPosition(playerColor: PlayerColor = currentPlayerColor) =
            getPlayerField(playerColor).gamePosition

    fun getConnectionInfo(
            playerColor: PlayerColor = currentPlayerColor,
            stepsLeft: Boolean = currentPlayer.areStepsLeft(),
            previousPosition: PositionData = currentPlayer.previousStep,
            phase: Phase = currentPhase
    ) = ConnectionInfo(getPlayerPosition(playerColor), stepsLeft, previousPosition, phase)

    fun getField(fieldId: Int, groupId: Int = 0) = testMap.groups[groupId].getField(fieldId)

    fun getPlayerField(player: PlayerColor = currentPlayerColor): SpaceField =
            graphicController.getPlayerField(player)

    fun getRandomConnectedField(): SpaceField {
        val currentField = currentPlayer.positionField.target
        val lastStep = currentPlayer.steps.last()
        return currentField.connections
                .find { !lastStep.isPosition(it.gamePosition) }
                ?.let { graphicController.getField(it.gamePosition) }
                ?: NONE_SPACE_FIELD
    }

    //
    // █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
    //██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
    //███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗
    //██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║
    //██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║
    //╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
    //
    fun nextPhase(color: PlayerColor = currentPlayerColor,
                  error: GameException? = null,
                  assertError: (Throwable) -> Boolean = {
                      error?.assertNextPhaseException(it) ?: false
                  },
                  assertSuccess: ((NextPhaseResult) -> Boolean) = { true }) {
        nextPhaseUseCase.buildUseCaseSingle(color)
                .test()
                .await()
                .apply { assertObserver(error, assertError, assertSuccess) }

    }

    fun dice(player: PlayerColor = currentPlayerColor,
             setDice: Int = -1,
             error: GameException? = null) {
        diceUsecase.buildUseCaseCompletable(player to setDice)
                .test()
                .await()
                .apply {
                    if (error == null) assertComplete()
                    else assertError { error.assertDiceException(it) }
                }
        waitForIt()
    }

    fun move(player: PlayerColor = currentPlayerColor,
             target: SpaceField = leftTopField,
             error: GameException? = null,
             assertError: (Throwable) -> Boolean = { error?.assertMoveException(it) ?: false },
             assertSuccess: (MoveInfo) -> Boolean = { true }) {
        moveUsecase.buildUseCaseSingle(player to target.gamePosition)
                .test()
                .await()
                .apply { assertObserver(error, assertError, assertSuccess) }
        waitForIt()
    }

    private fun <T> TestObserver<T>.assertObserver(
            error: GameException?,
            assertError: (Throwable) -> Boolean,
            assertSuccess: (T) -> Boolean) {
        if (error == null) assertComplete().assertValue {
            assertSuccess.invoke(it)
        }
        else assertError { assertError.invoke(it) }
    }

    fun waitForIt(time: Long = 5) {
        Thread.sleep(time)
    }

    fun getDBPlayer(player: PlayerColor, assertPlayer: (PlayerData) -> Boolean) {
        getPlayerUsecase.buildUseCaseSingle(player)
                .assertValue(assertPlayer)
    }

    fun getDBField(gamePosition: PositionData, assertField: (FieldData) -> Boolean) {
        getFieldUsecase.buildUseCaseSingle(gamePosition)
                .assertValue(assertField)
    }

    fun getDBMap(assertField: (MapData) -> Boolean) {
        getMapUsecase.buildUseCaseSingle()
                .assertValue(assertField)
    }

    private fun <T> Single<T>.assertValue(assertIt: (T) -> Boolean) {
        test().await()
                .assertComplete()
                .assertValue { assertIt(it) }
    }

}