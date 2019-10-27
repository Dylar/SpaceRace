package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool.getCommand
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class DiceCommand : BaseCommand() {

    companion object {
        fun get(playerData: PlayerData,
                maxResult: Int = DICE_MAX
        ) = getCommand(DiceCommand::class)
                .also {
                    it.maxResult = maxResult
                    it.DONT_USE_THIS_PLAYER_DATA = playerData
                }
    }

    @Inject
    protected lateinit var diceUsecase: DiceUsecase

    var maxResult: Int = DICE_MAX

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        compositDisposable += diceUsecase.execute(
                params = DONT_USE_THIS_PLAYER_DATA.playerColor to maxResult,
                onComplete = ::reset,
                onError = { reset() })
    }

}