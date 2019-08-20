package de.bitb.spacerace

import de.bitb.spacerace.TestActions.waitForIt
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.MapCollection.TEST_MAP
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.init.LoadGameUsecase
import javax.inject.Inject

val TEST_PLAYER_1 = PlayerColor.ORANGE
val TEST_PLAYER_2 = PlayerColor.GREEN
val DEFAULT_TEST_PLAYER = mutableListOf(TEST_PLAYER_1, TEST_PLAYER_2)

class SpaceEnvironment {

    @Inject
    lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase

    @Inject
    lateinit var diceUsecase: DiceUsecase

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
            mapCollection: MapCollection = TEST_MAP,
            vararg playerColor: PlayerColor = arrayOf()
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

    fun nextPhase(color: PlayerColor) {
        nextPhaseUseCase
                .buildUseCaseCompletable(color)
                .test()
                .await()
                .assertComplete()
    }

    fun dice(player: PlayerColor, maxResult: Int = 1) {
        diceUsecase.buildUseCaseCompletable(player to maxResult)
                .test()
                .await()
                .assertComplete()
    }
}