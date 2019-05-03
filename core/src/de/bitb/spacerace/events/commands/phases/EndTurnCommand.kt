package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ui.PlayerChangedUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class EndTurnCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    @Inject
    protected lateinit var playerChangedUsecase: PlayerChangedUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val playerController = game.gameController.playerController
        playerController.nextTurn()

        playerChangedUsecase.publishUpdate(getCurrentPlayer(game).playerData.playerColor)

        if (playerController.isRoundEnd()) {
            EventBus.getDefault().post(EndRoundCommand())
        }

    }

}