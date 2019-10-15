package de.bitb.spacerace.tests.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.*
import de.bitb.spacerace.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.exceptions.WrongPhaseException
import de.bitb.spacerace.model.enums.Phase
import org.junit.Test

class MoveUsecaseTest : GameTest() {

    @Test
    fun onlyCurrentPlayerCanMove_inMovePhase() {
        TestEnvironment()
                .initGame()
                .setToMovePhase()
                .dice()
                .apply {

                    val target = leftTopField
                    var position = currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MOVE)
                    assertPlayerOnField(TEST_PLAYER_1, position)
                    assertPlayerOnField(TEST_PLAYER_2, position)

                    //move player 2 -> failed
                    move(TEST_PLAYER_2, target, NotCurrentPlayerException(TEST_PLAYER_2))

                    position = currentPosition
                    assertNotSameField(target, position)
                    assertPlayerOnField(TEST_PLAYER_1, position)
                    assertPlayerOnField(TEST_PLAYER_2, position)

                    //move player 1 -> success
                    move(TEST_PLAYER_1, target)

                    position = currentPosition
                    assertSameField(target, position)
                    assertPlayerNotOnField(TEST_PLAYER_2, target)
                    assertPlayerOnField(TEST_PLAYER_1, target)
                }
    }

    @Test
    fun nobodyCanMove_InMain1Phase() {
        TestEnvironment()
                .apply {
                    initGame()

                    val target = leftTopField
                    var position = currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)
                    assertPlayerOnField(TEST_PLAYER_1, position)
                    assertPlayerOnField(TEST_PLAYER_2, position)

                    val moveAndCheck = fun() {
                        val check = fun() {
                            position = currentPosition
                            assertNotSameField(target, position)
                            assertPlayerOnField(TEST_PLAYER_1, position)
                            assertPlayerOnField(TEST_PLAYER_2, position)
                        }
                        //move player 2 -> failed
                        move(TEST_PLAYER_2, target, NotCurrentPlayerException(TEST_PLAYER_2))
                        check()
                        //move player 1 -> failed
                        move(TEST_PLAYER_1, target, WrongPhaseException(TEST_PLAYER_1, setOf(Phase.MOVE)))
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    dice()
                    moveAndCheck()
                }
    }

    @Test
    fun nobodyCanMove_InMain2Phase() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()

                    val target = centerTopField
                    var position = currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN2)
                    assertPlayerOnField(TEST_PLAYER_1, position)
                    assertPlayerNotOnField(TEST_PLAYER_2, position)

                    val moveAndCheck = fun() {
                        val check = fun() {
                            position = currentPosition
                            assertNotSameField(target, position)
                            assertPlayerOnField(TEST_PLAYER_1, position)
                            assertPlayerNotOnField(TEST_PLAYER_2, position)
                        }
                        //move player 2 -> failed
                        move(TEST_PLAYER_2, target, NotCurrentPlayerException(TEST_PLAYER_2))
                        check()
                        //move player 1 -> failed
                        move(TEST_PLAYER_1, target, WrongPhaseException(TEST_PLAYER_1, setOf(Phase.MOVE)))
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    dice()
                    moveAndCheck()
                }
    }

    //TODO test NoStepsLeftException
    //TODO test FieldsNotConnectedException

}