package de.bitb.spacerace.env

import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.*
import de.bitb.spacerace.core.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.MapCollection.TEST_MAP
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
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
    lateinit var playerController: PlayerController
    @Inject
    lateinit var fieldController: FieldController

    lateinit var winnerObserver: TestObserver<PlayerData>

    lateinit var testGame: TestGame
    val setup = Setup()

    val currentPlayer: PlayerData
        get() = playerController.currentPlayerData
    val currentPlayerColor: PlayerColor
        get() = currentPlayer.playerColor
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
            vararg playerColor: PlayerColor = arrayOf(),
            mapCollection: MapCollection = TEST_MAP,
            winAmount: Long = 1
    ) {
        WIN_AMOUNT = winAmount
        SELECTED_PLAYER.apply {
            clear()
            if (playerColor.isEmpty()) {
                addAll(DEFAULT_TEST_PLAYER)
            } else {
                addAll(playerColor)
            }
        }

        testGame = TestGame()
        testGame.initGame()

        TestGame.testComponent.inject(this)

        fieldController.spaceMap = mapCollection

        loadGameUsecase.buildUseCaseSingle(SELECTED_PLAYER)
                .test()
                .await()
                .assertComplete()

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

    fun getField(fieldId: Int, groupId: Int = 0) =
            fieldController.getField(groupId, fieldId)

    fun getPlayerField(player: PlayerColor = currentPlayerColor): SpaceField =
            graphicController.getPlayerField(player)

    fun getRandomConnectedField() =
            (currentPlayer to getPlayerField()).let { (player, currentField) ->
                currentField.connections.firstOrNull { connection ->
                    !connection.spaceField1.gamePosition.isPosition(fieldController.currentGoal ?: NONE_POSITION)
                            && !connection.spaceField2.gamePosition.isPosition(fieldController.currentGoal ?: NONE_POSITION)
                            && player.steps.last().let { lastStep ->
                        lastStep != connection.spaceField1.gamePosition || lastStep != connection.spaceField2.gamePosition
                    }
                }?.getOpposite(currentField) ?: NONE_FIELD
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
                  assertError: (Throwable) -> Boolean = { error?.assertNextPhaseException(it) ?: false },
                  assertSuccess: ((NextPhaseInfo) -> Boolean) = { true }) {
        nextPhaseUseCase.buildUseCaseSingle(color)
                .test()
                .await()
                .apply { assertObserver(error, assertError, assertSuccess) }
        waitForIt()
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
        if (error == null) assertComplete().assertValue {
            assertSuccess.invoke(it)
        }
        else assertError { assertError.invoke(it) }
    }

    fun waitForIt(time: Long = 100) {
        Thread.sleep(time)
    }

    fun getDBPlayer(player: PlayerColor, assertPlayer: (PlayerData) -> Boolean) {
        getPlayerUsecase.buildUseCaseSingle(player)
                .test()
                .await()
                .assertComplete()
                .assertValue { assertPlayer(it) }
    }

}