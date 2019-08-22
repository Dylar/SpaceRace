package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.action.StartNewRoundUsecase
import javax.inject.Inject

class StartNextRoundCommand() : BaseCommand(NONE_PLAYER_DATA) {

    @Inject
    protected lateinit var startNewRoundUsecase: StartNewRoundUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        startNewRoundUsecase.getResult()
    }

}