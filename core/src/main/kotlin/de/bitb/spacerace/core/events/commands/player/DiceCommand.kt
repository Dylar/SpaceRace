package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool.getCommand
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class DiceCommand : BaseCommand() {

    companion object {
        fun get(player: PlayerColor,
                maxResult: Int = DICE_MAX
        ) = getCommand(DiceCommand::class)
                .also {
                    it.maxResult = maxResult
                    it.player = player
                }
    }

    @Inject
    protected lateinit var diceUsecase: DiceUsecase

    var maxResult: Int = DICE_MAX

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        compositeDisposable += diceUsecase.execute(
                params = player to maxResult,
                onComplete = ::reset,
                onError = { reset() })
    }

}