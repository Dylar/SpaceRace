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
                }
                .also { env ->
                    val target = env.defaultField1
                    var position = env.currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MOVE)
                    assertPlayerOnField(env, TEST_PLAYER_1, position)
                    assertPlayerOnField(env, TEST_PLAYER_2, position)

                    //move player 2 -> failed
                    env.move(TEST_PLAYER_2, target)

                    position = env.currentPosition
                    assertNotSameField(target, position)
                    assertPlayerOnField(env, TEST_PLAYER_1, position)
                    assertPlayerOnField(env, TEST_PLAYER_2, position)

                    //move player 1 -> success
                    env.move(TEST_PLAYER_1, target)

                    position = env.currentPosition
                    assertSameField(target, position)
                    assertPlayerNotOnField(env, TEST_PLAYER_2, target)
                    assertPlayerOnField(env, TEST_PLAYER_1, target)
                }
    }

    @Test
    fun nobodyCanMove_InMain1Phase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                }
                .also { env ->
                    val target = env.defaultField1
                    var position = env.currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)
                    assertPlayerOnField(env, TEST_PLAYER_1, position)
                    assertPlayerOnField(env, TEST_PLAYER_2, position)

                    val moveAndCheck = fun() {
                        val check = fun() {
                            position = env.currentPosition
                            assertNotSameField(target, position)
                            assertPlayerOnField(env, TEST_PLAYER_1, position)
                            assertPlayerOnField(env, TEST_PLAYER_2, position)
                        }
                        //move player 2 -> failed
                        env.move(TEST_PLAYER_2, target)
                        check()
                        //move player 1 -> failed
                        env.move(TEST_PLAYER_1, target)
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    env.dice()
                    moveAndCheck()
                }
    }

    @Test
    fun nobodyCanMove_InMain2Phase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()
                }
                .also { env ->
                    val target = env.defaultField2
                    var position = env.currentPosition

                    //assert start
                    assertNotSameField(target, position)
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN2)
                    assertPlayerOnField(env, TEST_PLAYER_1, position)
                    assertPlayerNotOnField(env, TEST_PLAYER_2, position)

                    val moveAndCheck = fun() {
                        val check = fun() {
                            position = env.currentPosition
                            assertNotSameField(target, position)
                            assertPlayerOnField(env, TEST_PLAYER_1, position)
                            assertPlayerNotOnField(env, TEST_PLAYER_2, position)
                        }
                        //move player 2 -> failed
                        env.move(TEST_PLAYER_2, target)
                        check()
                        //move player 1 -> failed
                        env.move(TEST_PLAYER_1, target)
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    env.move()
                    moveAndCheck()
                }
    }


}