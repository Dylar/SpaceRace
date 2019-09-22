package de.bitb.spacerace.env

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.MapCreator
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.model.space.maps.initDefaultMap
import de.bitb.spacerace.usecase.game.action.*
import de.bitb.spacerace.usecase.game.getter.GetFieldUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.getter.GetSaveGameUsecase
import de.bitb.spacerace.usecase.game.getter.GetTargetableFieldUsecase
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameResult
import de.bitb.spacerace.usecase.game.init.LoadNewGameUsecase
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
    lateinit var gameController: GameController

    @Inject
    lateinit var loadNewGameUsecase: LoadNewGameUsecase

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
    lateinit var getSaveGameUsecase: GetSaveGameUsecase
    @Inject
    lateinit var getTargetableFieldUsecase: GetTargetableFieldUsecase

    @Inject
    lateinit var playerController: PlayerController

    lateinit var winnerObserver: TestObserver<PlayerData>

    lateinit var testGame: TestGame
    lateinit var testMap: SpaceMap

    val setup = Setup()

    val currentPlayerColor: PlayerColor
        get() = playerController.currentColor
    //    playerColorDispenser.publisher.test().await().values().first()
    val currentPlayer: PlayerData
        get() = getDBPlayer(currentPlayerColor)
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
        val map = testMap.initDefaultMap()
        val config = LoadGameConfig(playerColor.toList(), map)
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

    fun setToMovePhase(setDice: Int = 1) {
        dice(setDice = -setDice)
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

    fun getDBField(position: PositionData) =
            getFieldUsecase.buildUseCaseSingle(position).test().await()
                    .assertComplete().values().first()

    fun getPlayerPosition(player: PlayerColor = currentPlayerColor) =
            getDBPlayer(player).gamePosition

    fun getDBPlayer(player: PlayerColor) =
            getPlayerUsecase.buildUseCaseSingle(player).test().await()
                    .assertComplete().values().first()

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

}