package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.usecase.game.PlayerTurnChangedUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class EndTurnCommand(playerData: PlayerData) : PhaseCommand(playerData) {

    @Inject
    protected lateinit var playerTurnChangedUsecase: PlayerTurnChangedUsecase

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        playerController.nextTurn()

        playerTurnChangedUsecase.publishUpdate(playerData.playerColor)

        if (playerController.isRoundEnd()) {
            EventBus.getDefault().post(EndRoundCommand())
        }

    }

}