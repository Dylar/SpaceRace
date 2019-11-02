package de.bitb.spacerace.env

import de.bitb.spacerace.core.controller.GameController
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.exceptions.GameException
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.game.action.items.ActivateItemUsecase
import de.bitb.spacerace.usecase.game.action.items.DisposeItemUsecase
import de.bitb.spacerace.usecase.game.action.items.EquipItemUsecase
import de.bitb.spacerace.usecase.game.getter.GetSaveGameUsecase
import de.bitb.spacerace.usecase.game.getter.GetTargetableFieldUsecase
import de.bitb.spacerace.usecase.game.init.LoadNewGameUsecase
import de.bitb.spacerace.usecase.game.trigger.StartNewRoundUsecase
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
    lateinit var mapDataSource: MapDataSource


    @Inject
    lateinit var startNewRoundUsecase: StartNewRoundUsecase
    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase
    @Inject
    lateinit var diceUsecase: DiceUsecase
    @Inject
    lateinit var moveUsecase: MoveUsecase

    @Inject
    lateinit var equipItemUsecase: EquipItemUsecase
    @Inject
    lateinit var activateItemUsecase: ActivateItemUsecase
    @Inject
    lateinit var disposeItemUsecase: DisposeItemUsecase

    @Inject
    lateinit var playerDataSource: PlayerDataSource
    @Inject
    lateinit var getSaveGameUsecase: GetSaveGameUsecase
    @Inject
    lateinit var getTargetableFieldUsecase: GetTargetableFieldUsecase

    @Inject
    lateinit var playerController: PlayerController

    lateinit var winnerObserver: TestObserver<PlayerData>

    lateinit var testGame: TestGame

    val setup = TestSystemSetup()

    val currentPlayerColor: PlayerColor
        get() = playerController.currentColor
    //    playerColorDispenser.publisher.test().await().values().first()
    val currentPlayer: PlayerData
        get() = getDBPlayer(currentPlayerColor)
    val currentPosition: PositionData
        get() = currentPlayer.gamePosition

    lateinit var leftBottomField: PositionData
    lateinit var leftTopField: PositionData
    lateinit var centerTopField: PositionData
    lateinit var centerBottomField: PositionData
    lateinit var leftSideField: PositionData
    lateinit var circleStep1Field: PositionData
    lateinit var circleStep2Field: PositionData
    lateinit var bridgeField: PositionData

    //
//    ██████╗ ███████╗████████╗████████╗███████╗██████╗
//    ██╔════╝ ██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗
//    ██║  ███╗█████╗     ██║      ██║   █████╗  ██████╔╝
//    ██║   ██║██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗
//    ╚██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║
//    ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝
//

    fun getPlayerPosition(player: PlayerColor = currentPlayerColor) =
            getDBPlayer(player).gamePosition

    fun getDBPlayer(player: PlayerColor) = playerDataSource.getDBPlayerByColor(player).first()

    fun getDBField(fieldId: Long) =
            mapDataSource.getDBFields(fieldId).first()

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
            assertSuccess: (T) -> Boolean
    ) {
        if (error == null) assertComplete().assertValue { assertSuccess(it) }
        else assertError { assertError(it) }
    }

    fun waitForIt(time: Long = 10) {
        Thread.sleep(time)
    }

}