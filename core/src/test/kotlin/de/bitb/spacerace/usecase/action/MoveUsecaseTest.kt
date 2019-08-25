package de.bitb.spacerace.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.model.enums.Phase
import org.junit.Test

class MoveUsecaseTest : GameTest() {

    @Test
    fun onlyCurrentPlayerCanMove_inMovePhase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    dice()


                    val target = defaultField1
                    var position = currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MOVE)
                    assertPlayerOnField(TEST_PLAYER_1, position)
                    assertPlayerOnField(TEST_PLAYER_2, position)

                    //move player 2 -> failed
                    move(TEST_PLAYER_2, target)

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
        SpaceEnvironment()
                .apply {
                    initGame()


                    val target = defaultField1
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
                        move(TEST_PLAYER_2, target)
                        check()
                        //move player 1 -> failed
                        move(TEST_PLAYER_1, target)
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
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()


                    val target = defaultField2
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
                        move(TEST_PLAYER_2, target)
                        check()
                        //move player 1 -> failed
                        move(TEST_PLAYER_1, target)
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    move()
                    moveAndCheck()
                }
    }


}