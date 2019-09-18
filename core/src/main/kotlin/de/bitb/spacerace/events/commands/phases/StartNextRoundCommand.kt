package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandPool
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.trigger.StartNewRoundUsecase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class StartNextRoundCommand : BaseCommand() {

    companion object {
        fun get() =
                CommandPool.getCommand(StartNextRoundCommand::class)
    }

    @Inject
    protected lateinit var startNewRoundUsecase: StartNewRoundUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        compositDisposable += startNewRoundUsecase.getResult(
                onSuccess = { reset() },
                onError = { reset() })
    }

}