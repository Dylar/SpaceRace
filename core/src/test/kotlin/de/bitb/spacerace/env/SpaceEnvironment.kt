package de.bitb.spacerace.env

import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.assertCurrentPhase
import de.bitb.spacerace.core.assertSameField
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.MapCollection.TEST_MAP
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import io.reactivex.observers.TestObserver
import javax.inject.Inject

val TEST_PLAYER_1 = PlayerColor.ORANGE
val TEST_PLAYER_2 = PlayerColor.GREEN
val TEST_PLAYER_3 = PlayerColor.PINK
val DEFAULT_TEST_PLAYER = mutableListOf(TEST_PLAYER_1, TEST_PLAYER_2)

class SpaceEnvironment : DefaultFunction by DEFAULT {

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

    val goalField: SpaceField
        get() = getField(0)
    val defaultField1: SpaceField
        get() = getField(4)
    val defaultField2: SpaceField
        get() = getField(3)

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

        assertCurrentPhase(this, Phase.MOVE)
    }

    fun setToMain2Phase() {
        setToMovePhase()

        move()
        nextPhase()

        assertCurrentPhase(this, Phase.MAIN2)
    }

    fun changePlayerTo(player: PlayerColor) {
        if (player == currentPlayerColor) {
            return
        } else {
            setToMain2Phase()
            nextPhase()
            changePlayerTo(player)
        }
    }

    fun moveToGoal() {
        setToMovePhase()
        move(target = goalField)
        assertSameField(getPlayerField(), goalField)
    }

//
//    ██████╗ ███████╗████████╗████████╗███████╗██████╗
//    ██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗
//    ██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝
//    ██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗
//    ╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║
//    ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝
//


    fun getField(fieldId: Int, groupId: Int = 0) =
            fieldController.getField(groupId, fieldId)

    fun getPlayerField(player: PlayerColor = currentPlayerColor): SpaceField =
            getPlayerField(playerController, fieldController, player)

    fun getRandomConnectedField() =
            (currentPlayer to getPlayerField()).let { (player, currentField) ->
                currentField.connections.first { connection ->
                    connection.spaceField1 != fieldController.currentGoal
                            && connection.spaceField2 != fieldController.currentGoal
                            && player.steps.last().let { lastStep ->
                        lastStep != connection.spaceField1.gamePosition || lastStep != connection.spaceField2.gamePosition
                    }
                }.getOpposite(currentField)
            }


    //
    // █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
    //██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
    //███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗
    //██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║
    //██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║
    //╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
    //
    fun nextPhase(color: PlayerColor = currentPlayerColor) {
        nextPhaseUseCase
                .buildUseCaseCompletable(color)
                .test()
                .await()
                .assertComplete()
        waitForIt()
    }

    fun dice(player: PlayerColor = currentPlayerColor, setDice: Int = -1) {
        diceUsecase.buildUseCaseCompletable(player to setDice)
                .test()
                .await()
                .assertComplete()
        waitForIt()
    }

    fun move(player: PlayerColor = currentPlayerColor,
             target: SpaceField = defaultField1) {
        moveUsecase.buildUseCaseCompletable(player to target)
                .test()
                .await()
                .assertComplete()
        waitForIt()
    }

    fun waitForIt(time: Long = 100) {
        Thread.sleep(time)
    }

}