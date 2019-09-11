package de.bitb.spacerace.env

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.*
import de.bitb.spacerace.core.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.maps.MapCreator
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.game.action.*
import de.bitb.spacerace.usecase.game.getter.GetFieldUsecase
import de.bitb.spacerace.usecase.game.getter.GetMapUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameResult
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
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

    val currentPlayerColor: PlayerColor
        get() = playerController.currentColor
//    playerColorDispenser.publisher.test().await().values().first()
    val currentPlayer: PlayerData
        get() = getDBPlayer(currentPlayerColor)
    val currentPhase: Phase
        get() = currentPlayer.phase
    val currentPosition: PositionData
        get() = currentPlayer.gamePosition

    val centerBottomField: PositionData
        get() = getFieldPosition(0)
    val leftBottomField: PositionData
        get() = getFieldPosition(1)
    val leftTopField: PositionData
        get() = getFieldPosition(4)
    val centerTopField: PositionData
        get() = getFieldPosition(3)

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
            assertSuccess: (LoadGameResult) -> Boolean = { true }
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
                .apply { assertObserver(error, assertError, assertSuccess) }

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
        assertSameField(getPlayerPosition(), centerBottomField)
    }

    //
//    ██████╗ ███████╗████████╗████████╗███████╗██████╗
//    ██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗
//    ██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝
//    ██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗
//    ╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║
//    ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝
//

    private fun getFieldPosition(fieldId: Int, groupId: Int = 0) = testMap.groups[groupId].getField(fieldId).gamePosition

    fun getPlayerPosition(player: PlayerColor = currentPlayerColor) =
            getDBPlayer(player).gamePosition

    fun getConnectionInfo(
            playerColor: PlayerColor = currentPlayerColor,
            stepsLeft: Boolean = currentPlayer.areStepsLeft(),
            previousPosition: PositionData = currentPlayer.previousStep,
            phase: Phase = currentPhase
    ) = ConnectionResult(getDBPlayer(playerColor).gamePosition, stepsLeft, previousPosition, phase)


//    fun getRandomConnectedField(): PositionData {
//        val currentField = currentPlayer.positionField.target
//        val lastStep = currentPlayer.steps.last()
//        return (currentField.connections
//                .find { it.fieldType != FieldType.GOAL && !lastStep.isPosition(it.gamePosition) }
//                ?.let { graphicController.getFieldGraphic(it.gamePosition) }
//                ?: NONE_SPACE_FIELD).gamePosition
//    }

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
                  assertError: (Throwable) -> Boolean = { error?.assertNextPhaseException(it) ?: false },
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
             target: PositionData = leftTopField,
             error: GameException? = null,
             assertError: (Throwable) -> Boolean = { error?.assertMoveException(it) ?: false },
             assertSuccess: (MoveResult) -> Boolean = { true }) {
        moveUsecase.buildUseCaseSingle(player to target)
                .test()
                .await()
                .apply { assertObserver(error, assertError, assertSuccess) }
        waitForIt()
    }

    private fun <T> TestObserver<T>.assertObserver(
            error: GameException?,
            assertError: (Throwable) -> Boolean,
            assertSuccess: (T) -> Boolean) {
        if (error == null) assertComplete().assertValue { assertSuccess(it) }
        else assertError { assertError(it) }
    }

    fun waitForIt(time: Long = 5) {
        Thread.sleep(time)
    }

    fun getDBPlayer(player: PlayerColor) =
            getPlayerUsecase.buildUseCaseSingle(player).test().await()
                    .assertComplete().values().first()

    fun createConnection(field1: PositionData, field2: PositionData): SpaceConnection =
            SpaceConnection(graphicController.getFieldGraphic(field1), graphicController.getFieldGraphic(field2))

}