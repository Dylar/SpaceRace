package de.bitb.spacerace

import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert


fun assertCurrentPlayer(env: SpaceEnvironment, testPlayer1: PlayerColor) =
        env.playerController.currentPlayerData
                .also {
                    Assert.assertThat(it.playerColor, `is`(testPlayer1))
                }

fun assertCurrentPhase(env: SpaceEnvironment, phase: Phase) =
        env.playerController.currentPlayerData
                .also {
                    Assert.assertThat(it.phase, `is`(phase))
                }

