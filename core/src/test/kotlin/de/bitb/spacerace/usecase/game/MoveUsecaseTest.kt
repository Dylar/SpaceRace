package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.game.TestActions.Action.DICE
import de.bitb.spacerace.game.TestActions.Action.MOVE
import de.bitb.spacerace.model.enums.Phase
import org.junit.Test

class MoveUsecaseTest : GameTest() {

    @Test
    fun onlyCurrentPlayerCanMove_inMovePhase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    DICE(currentPlayerColor).doAction(this)
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
                    MOVE(TEST_PLAYER_2, env.defaultField1).doAction(env)

                    position = env.currentPosition
                    assertNotSameField(target, position)
                    assertPlayerOnField(env, TEST_PLAYER_1, position)
                    assertPlayerOnField(env, TEST_PLAYER_2, position)

                    //move player 1 -> success
                    MOVE(TEST_PLAYER_1, env.defaultField1).doAction(env)

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
                        MOVE(TEST_PLAYER_2, env.defaultField1).doAction(env)
                        check()
                        //move player 1 -> failed
                        MOVE(TEST_PLAYER_1, env.defaultField1).doAction(env)
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    DICE(env.currentPlayerColor)
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
                        MOVE(TEST_PLAYER_2, env.defaultField1).doAction(env)
                        check()
                        //move player 1 -> failed
                        MOVE(TEST_PLAYER_1, env.defaultField1).doAction(env)
                        check()
                    }

                    moveAndCheck()
                    //after dice still not movable
                    DICE(env.currentPlayerColor)
                    moveAndCheck()
                }
    }


}