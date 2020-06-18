package de.bitb.spacerace.core.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool
import de.bitb.spacerace.usecase.game.trigger.StartNewRoundUsecase
import io.reactivex.rxjava3.kotlin.plusAssign
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

    override fun execute() {
        compositeDisposable += startNewRoundUsecase.getResult(
                onSuccess = { reset() },
                onError = { reset() })
    }

}

