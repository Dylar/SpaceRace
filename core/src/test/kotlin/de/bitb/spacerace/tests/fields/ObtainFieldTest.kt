package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.usecase.game.action.NextPhaseResult

abstract class ObtainFieldTest : GameTest() {

    fun TestEnvironment.obtainField(
            fieldType: FieldType,
            onNextPhase: (NextPhaseResult) -> Boolean = { true }
    ) = this.apply {
        initGame(map = createTestMap(firstStep = fieldType))
        setToMovePhase()
        move()
        nextPhase(assertSuccess = onNextPhase)
    }
}