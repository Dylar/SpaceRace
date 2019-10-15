package de.bitb.spacerace.env

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.assertDiceException
import de.bitb.spacerace.core.assertMoveException
import de.bitb.spacerace.core.assertNextPhaseException
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.GameException
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.game.action.*
import de.bitb.spacerace.usecase.game.getter.GetFieldUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.getter.GetSaveGameUsecase
import de.bitb.spacerace.usecase.game.getter.GetTargetableFieldUsecase
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import de.bitb.spacerace.usecase.game.init.LoadNewGameUsecase
import io.reactivex.observers.TestObserver
import javax.inject.Inject

val TEST_PLAYER_1 = PlayerColor.ORANGE
val TEST_PLAYER_2 = PlayerColor.GREEN
val TEST_PLAYER_3 = PlayerColor.PINK
val DEFAULT_TEST_PLAYER = mutableListOf(TEST_PLAYER_1, TEST_PLAYER_2)

class TestEnvironment {

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

    val setup = TestSystemSetup()

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


    fun <T> TestObserver<T>.assertObserver(
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