package de.bitb.spacerace.scenario

import de.bitb.spacerace.controller.toConnectionInfo
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
                                connectionInfo = getConnectionInfo(
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
                                    connectionInfo = getConnectionInfo())

                    assertSameField(getPlayerField().gamePosition, leftBottomField)

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
                            connectionInfo = getConnectionInfo())
                    assertConnectionAfterMove(
                            connection = connectionBack,
                            assertSuccess = {
                                it.toConnectionInfo().let { connectionInfo ->
                                    checkConnection(
                                            isConnected = true,
                                            connection = createConnection(leftBottomField, leftTopField),
                                            connectionInfo = connectionInfo)
                                            && checkConnection(
                                            isConnected = true,
                                            connection = createConnection(leftBottomField, centerBottomField),
                                            connectionInfo = connectionInfo)
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
                    getConnectionInfo().also { info ->
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftBottomField, leftTopField),
                                connectionInfo = info))
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftBottomField, centerBottomField),
                                connectionInfo = info))
                    }

                    move()

                    getConnectionInfo().also { info ->
                        assertTrue(checkConnection(
                                isConnected = true,
                                connection = createConnection(leftTopField, leftBottomField),
                                connectionInfo = info))
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, centerTopField),
                                connectionInfo = info))
                    }

                    nextPhase(currentPlayerColor)

                    getConnectionInfo().also { info ->
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, leftBottomField),
                                connectionInfo = info))
                        assertTrue(checkConnection(
                                connection = createConnection(leftTopField, centerTopField),
                                connectionInfo = info))
                    }
                }
    }

}