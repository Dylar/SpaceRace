package de.bitb.spacerace.tests.scenario

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.*
import de.bitb.spacerace.model.objecthandling.PositionData
import org.junit.Test

class ConnectionTest : GameTest() {

    @Test
    fun startGame_noConnections_bothPlayer_currentPlayerGotInMove() {
        TestEnvironment()
                .apply {
                    initGame()

                    val connection1 = listOf(leftBottomField, leftTopField)
                    val connection2 = listOf(leftBottomField, centerTopField)
                    val connectionGoal = listOf(leftBottomField, centerBottomField)
                    val connectionBack = listOf(leftTopField, leftBottomField)

                    fun assertConnectionPlayer2(con: List<PositionData>) {
                        assertConnection(
                                playerColor = TEST_PLAYER_2,
                                connection = con)
                    }

                    fun assertConnectionPlayer1(con: List<PositionData>, isConnected: Boolean = false) =
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
                            isConnected = true,
                            connection = connection1,
                            assertSuccess = {
                                checkConnection(
                                        fields = it.targetableFields,
                                        isConnected = true,
                                        connection = listOf(leftBottomField, leftTopField)
                                ) && checkConnection(
                                        fields = it.targetableFields,
                                        isConnected = true,
                                        connection = listOf(leftBottomField, centerBottomField))
                            }
                    )

                    assertConnectionPlayer2(connection1)
                    assertConnectionPlayer2(connection2)
                    assertConnectionPlayer2(connectionGoal)
                }
    }


    @Test
    fun startMove_currentPlayer_allConnected_moveDone_noneConnected() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    val conLeftBotTop = listOf(leftBottomField, leftTopField)
                    val conLeftTopCenter = listOf(leftTopField, centerTopField)
                    val conLeftBotCenter = listOf(leftBottomField, centerBottomField)

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
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(setDice = 2)

                    val conLeftBotTop = listOf(leftBottomField, leftTopField)
                    val conLeftTopCenter = listOf(leftTopField, centerTopField)
                    val conLeftBotCenter = listOf(leftBottomField, centerBottomField)

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