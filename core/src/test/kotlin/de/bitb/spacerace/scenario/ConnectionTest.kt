package de.bitb.spacerace.scenario

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.model.space.fields.SpaceConnection
import org.junit.Test

class ConnectionTest : GameTest() {

    @Test
    fun startGame_noConnections_bothPlayer_currentPlayerGotInMove() {
        SpaceEnvironment()
                .apply {
                    initGame()

                    val connection1 = createConnection(leftBottomField, leftTopField)
                    val connection2 = createConnection(leftBottomField, centerTopField)
                    val connectionGoal = createConnection(leftBottomField, centerBottomField)
                    val connectionBack = createConnection(leftTopField, leftBottomField)

                    fun assertConnectionPlayer2(con: SpaceConnection) {
                        assertConnection(
                                playerColor = TEST_PLAYER_2,
                                connection = con)
                    }

                    fun assertConnectionPlayer1(con: SpaceConnection, isConnected: Boolean = false) =
                            assertConnection(
                                    isConnected = isConnected,
                                    connection = con)

                    assertSameField(getPlayerPosition(), leftBottomField)

                    assertConnectionPlayer1(connection1)
                    assertConnectionPlayer1(connection2)
                    assertConnectionPlayer1(connectionGoal)
                    assertConnectionPlayer2(connection1)
                    assertConnectionPlayer2(connection2)
                    assertConnectionPlayer2(connectionGoal)

                    setToMovePhase()
                    move(target = leftTopField)
                    assertConnection(
                            isConnected = true,
                            connection = connectionBack)
                    assertConnectionAfterMove(
                            connection = connectionBack,
                            assertSuccess = {
                                checkConnection(
                                        fields = it.targetableFields,
                                        isConnected = true,
                                        connection = createConnection(leftBottomField, leftTopField)
                                ) && checkConnection(
                                        fields = it.targetableFields,
                                        isConnected = true,
                                        connection = createConnection(leftBottomField, centerBottomField))
                            }
                    )

                    assertConnectionPlayer2(connection1)
                    assertConnectionPlayer2(connection2)
                    assertConnectionPlayer2(connectionGoal)
                }
    }


    @Test
    fun startMove_currentPlayer_allConnected_moveDone_noneConnected() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    val conLeftBotTop = createConnection(leftBottomField, leftTopField)
                    val conLeftTopCenter = createConnection(leftTopField, centerTopField)
                    val conLeftBotCenter = createConnection(leftBottomField, centerBottomField)

                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotTop)
                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotCenter)
                    assertConnection(
                            connection = conLeftTopCenter)
                    move()

                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotTop)
                    assertConnection(
                            connection = conLeftBotCenter)
                    assertConnection(
                            connection = conLeftTopCenter)

                    nextPhase(currentPlayerColor)

                    assertConnection(
                            connection = conLeftBotTop)
                    assertConnection(
                            connection = conLeftBotCenter)
                    assertConnection(
                            connection = conLeftTopCenter)
                }
    }


    @Test
    fun startMove2Steps_currentPlayer_allConnected_move1Step_stillConnections_moveDone_noneConnected() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(setDice = 2)

                    val conLeftBotTop = createConnection(leftBottomField, leftTopField)
                    val conLeftTopCenter = createConnection(leftTopField, centerTopField)
                    val conLeftBotCenter = createConnection(leftBottomField, centerBottomField)

                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotTop)
                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotCenter)
                    assertConnection(
                            connection = conLeftTopCenter)
                    move()

                    assertConnection(
                            isConnected = true,
                            connection = conLeftBotTop)
                    assertConnection(
                            connection = conLeftBotCenter)
                    assertConnection(
                            isConnected = true,
                            connection = conLeftTopCenter)

                    move(target = centerTopField)

                    assertConnection(
                            connection = conLeftBotTop)
                    assertConnection(
                            connection = conLeftBotCenter)
                    assertConnection(
                            isConnected = true,
                            connection = conLeftTopCenter)

                    nextPhase(currentPlayerColor)

                    assertConnection(
                            connection = conLeftBotTop)
                    assertConnection(
                            connection = conLeftBotCenter)
                    assertConnection(
                            connection = conLeftTopCenter)
                }
    }
}