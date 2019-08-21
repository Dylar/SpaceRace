package de.bitb.spacerace

import de.bitb.spacerace.TestActions.Action.*
import de.bitb.spacerace.TestActions.waitForIt
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.MapCollection.TEST_MAP
import de.bitb.spacerace.usecase.game.GetPlayerUsecase
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.init.LoadGameUsecase
import javax.inject.Inject

val TEST_PLAYER_1 = PlayerColor.ORANGE
val TEST_PLAYER_2 = PlayerColor.GREEN
val TEST_PLAYER_3 = PlayerColor.PINK
val DEFAULT_TEST_PLAYER = mutableListOf(TEST_PLAYER_1, TEST_PLAYER_2)

class SpaceEnvironment : DefaultFunction by DEFAULT {

    @Inject
    lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    lateinit var getPlayerUsecase: GetPlayerUsecase

    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase

    @Inject
    lateinit var diceUsecase: DiceUsecase

    @Inject
    lateinit var moveUsecase: MoveUsecase

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var fieldController: FieldController

    lateinit var testGame: TestGame
    val setup = Setup()

    val currentPlayer: PlayerData
        get() = playerController.currentPlayerData
    val currentPlayerColor: PlayerColor
        get() = currentPlayer.playerColor

    fun initGame(
            vararg playerColor: PlayerColor = arrayOf(),
            mapCollection: MapCollection = TEST_MAP
    ) {

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

        testGame.initGameObserver()

        waitForIt()
    }

    fun setToMovePhase(setDice: Int = -1) {
        DICE(currentPlayerColor, setDice).doAction(this)
        NEXT_PHASE(currentPlayerColor).doAction(this)

        assertCurrentPhase(this, Phase.MOVE)
    }

    fun setToMain2Phase() {
        setToMovePhase()

        val target: SpaceField = getField(0, 4)
        MOVE(currentPlayerColor, target).doAction(this)
        NEXT_PHASE(currentPlayerColor).doAction(this)

        assertCurrentPhase(this, Phase.MAIN2)
    }

    fun changePlayerTo(player: PlayerColor) {
        if (player == currentPlayerColor) {
            return
        } else {
            setToMain2Phase()
            NEXT_PHASE(currentPlayerColor).doAction(this)
            changePlayerTo(player)
        }
    }

    fun nextPhase(color: PlayerColor) {
        nextPhaseUseCase
                .buildUseCaseCompletable(color)
                .test()
                .await()
                .assertComplete()
    }

    fun dice(player: PlayerColor, maxResult: Int = -1) {
        diceUsecase.buildUseCaseCompletable(player to maxResult)
                .test()
                .await()
                .assertComplete()
    }

    fun move(player: PlayerColor, target: SpaceField) {
        moveUsecase.buildUseCaseCompletable(player to target)
                .test()
                .await()
                .assertComplete()
    }

    fun getCurrentField() = fieldController.getField(playerController.currentPlayer.gamePosition)
    fun getRandomTarget() =
            (currentPlayer to getCurrentField()).let { (player, currentField) ->
                currentField.connections
                        .first { connection ->
                            connection.spaceField1 != fieldController.currentGoal
                                    && connection.spaceField2 != fieldController.currentGoal
                                    && player.steps.last().let { lastStep ->
                                lastStep != connection.spaceField1.gamePosition || lastStep != connection.spaceField2.gamePosition
                            }
                        }
                        .getOpposite(currentField)
            }

    fun getField(groupId: Int, fieldId: Int) = fieldController.getField(groupId, fieldId)
    fun getPlayerField(player: PlayerColor): SpaceField = getPlayerField(playerController, fieldController, player)

}