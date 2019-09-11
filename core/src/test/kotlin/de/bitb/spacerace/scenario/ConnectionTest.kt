package de.bitb.spacerace.scenario

import de.bitb.spacerace.controller.toConnectionResult
import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.space.fields.SpaceConnection
import junit.framework.Assert.assertTrue
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
                                connection = con,
                                connectionResult = getConnectionResult(
                                        playerColor = TEST_PLAYER_2,
                                        stepsLeft = false,
                                        previousPosition = NONE_POSITION
                                )
                        )
                    }

                    fun assertConnectionPlayer1(con: SpaceConnection, isConnected: Boolean = false) =
                            assertConnection(
                                    isConnected = isConnected,
                                    connection = con,
                                    connectionResult = getConnectionResult())

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
                            connection = connectionBack,
                            connectionResult = getConnectionResult())
                    assertConnectionAfterMove(
                            connection = connectionBack,
                            assertSuccess = {
                                it.toConnectionResult().let { connectionResult ->
                                    checkConnection(
                                            isConnected = true,
                                            connection = createConnection(leftBottomField, leftTopField),
                                            connectionResult = connectionResult)
                                            && checkConnection(
                                            isConnected = true,
                                            connection = createConnection(leftBottomField, centerBottomField),
                                            connectionResult = connectionResult)
                                }
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
                    getConnectionResult().also { info ->
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftBottomField, leftTopField),
                                connectionResult = info))
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftBottomField, centerBottomField),
                                connectionResult = info))
                    }

                    move()

                    getConnectionResult().also { info ->
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftTopField, leftBottomField),
                                connectionResult = info))
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, centerTopField),
                                connectionResult = info))
                    }

                    nextPhase(currentPlayerColor)

                    getConnectionResult().also { info ->
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, leftBottomField),
                                connectionResult = info))
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, centerTopField),
                                connectionResult = info))
                    }
                }
    }

}