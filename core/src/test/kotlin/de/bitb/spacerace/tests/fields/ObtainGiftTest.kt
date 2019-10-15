package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.*
import org.junit.Test

class ObtainGiftTest : GameTest() {

    @Test
    fun obtainGift_receiveItemIntoStorage() {
        TestEnvironment()
                .initGame()
                .setToMovePhase()
                .move()
                .apply {

                }
    }
}