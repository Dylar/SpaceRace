package de.bitb.spacerace.core

import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert


fun assertCurrentPlayer(env: SpaceEnvironment, testPlayer: PlayerColor) =
        env.playerController.currentPlayerData
                .also {
                    Assert.assertThat(it.playerColor, `is`(testPlayer))
                }

fun assertNotCurrentPlayer(env: SpaceEnvironment, testPlayer: PlayerColor) =
        env.playerController.currentPlayerData
                .also {
                    Assert.assertThat(it.playerColor, `is`(not(testPlayer)))
                }

fun assertCurrentPhase(env: SpaceEnvironment, phase: Phase) =
        env.playerController.currentPlayerData
                .also {
                    Assert.assertThat(it.phase, `is`(phase))
                }

fun assertTargetNotSame(field1: SpaceField, field2: SpaceField) =
        Assert.assertThat(field1, `is`(not(field2)))

fun assertPlayerOnField(env: SpaceEnvironment, player: PlayerColor, field: SpaceField) =
        env.getPlayerUsecase
                .buildUseCaseSingle(player)
                .test()
                .await()
                .assertComplete()
                .assertValue {
                    env.getPlayerField(env.playerController, env.fieldController, player) == field
//                Assert.assertThat(env.currentPlayerUseCase., `is`(not(field2)))
                }
