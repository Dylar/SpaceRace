package de.bitb.spacerace.env

import de.bitb.spacerace.core.*
import de.bitb.spacerace.core.exceptions.GameException
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import de.bitb.spacerace.usecase.game.action.NextPhaseResult
import de.bitb.spacerace.usecase.game.action.items.ActivateItemConfig
import de.bitb.spacerace.usecase.game.action.items.DisposeItemConfig
import de.bitb.spacerace.usecase.game.action.items.EquipItemConfig
import de.bitb.spacerace.usecase.game.action.items.UseItemResult

fun TestEnvironment.nextPhase(
        color: PlayerColor = currentPlayerColor,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertNextPhaseException(it) ?: false },
        assertSuccess: ((NextPhaseResult) -> Boolean) = { true }
) = this.apply {
    nextPhaseUseCase.buildUseCaseSingle(color).test().await()
            .assertObserver(error, assertError, assertSuccess)
    waitForIt()
}

fun TestEnvironment.dice(
        player: PlayerColor = currentPlayerColor,
        setDice: Int = -1,
        error: GameException? = null
) = this.apply {
    diceUsecase.buildUseCaseCompletable(player to setDice).test().await()
            .apply {
                if (error == null) assertComplete()
                else assertError { error.assertDiceException(it) }
            }
}

fun TestEnvironment.move(
        player: PlayerColor = currentPlayerColor,
        target: PositionData = leftTopField,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertMoveException(it) ?: false },
        assertSuccess: (MoveResult) -> Boolean = { true }
) = this.apply {
    moveUsecase.buildUseCaseSingle(player to target).test().await()
            .assertObserver(error, assertError, assertSuccess)
}

fun TestEnvironment.equipItem(
        itemInfo: ItemInfo,
        player: PlayerColor = currentPlayerColor,
        equip: Boolean = true,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertEquipException(it) ?: false },
        assertSuccess: (UseItemResult) -> Boolean = { true }
) = this.apply {
    val config = EquipItemConfig(player, itemInfo.type, equip)
    equipItemUsecase.buildUseCaseSingle(config).test().await()
            .assertObserver(error, assertError, assertSuccess)
}

fun TestEnvironment.activateItem(
        itemInfo: ItemInfo,
        player: PlayerColor = currentPlayerColor,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertActivateException(it) ?: false },
        assertSuccess: (UseItemResult) -> Boolean = { true }
) = this.apply {
    val config = ActivateItemConfig(player, itemInfo.type)
    activateItemUsecase.buildUseCaseSingle(config).test().await()
            .assertObserver(error, assertError, assertSuccess)
}

fun TestEnvironment.disposeItem(
        itemInfo: ItemInfo,
        player: PlayerColor = currentPlayerColor,
        error: GameException? = null,
        assertError: (Throwable) -> Boolean = { error?.assertActivateException(it) ?: false },
        assertSuccess: (UseItemResult) -> Boolean = { true }
) = this.apply {
    val config = DisposeItemConfig(player, itemInfo.type)
    disposeItemUsecase.buildUseCaseSingle(config).test().await()
            .assertObserver(error, assertError, assertSuccess)
}