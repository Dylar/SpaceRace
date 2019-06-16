package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseCommand(playerData: PlayerData) : PhaseCommand(playerData) {

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(playerData)
    }

    override fun execute(game: MainGame) {
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")
        when (playerData.phase) {
            Phase.MAIN1 -> EventBus.getDefault().post(StartMain1Command(this.playerData))
            Phase.MOVE -> EventBus.getDefault().post(StartMoveCommand(this.playerData))
            Phase.MAIN2 -> EventBus.getDefault().post(StartMain2Command(this.playerData))
            Phase.END_TURN -> EventBus.getDefault().post(EndTurnCommand(this.playerData))
            Phase.END_ROUND -> throw UnsupportedOperationException("END ROUND NEXT?")
        }

//        updatePlayerUsecase.execute(listOf(playerData))
    }

}