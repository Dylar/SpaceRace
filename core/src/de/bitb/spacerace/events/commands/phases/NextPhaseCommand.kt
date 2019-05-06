package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(getPlayerData(game, playerColor))
    }

    override fun execute(game: MainGame) {
        val playerData = getPlayerData(game, playerColor)
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")
        when (playerData.phase) {
            Phase.MAIN1 -> EventBus.getDefault().post(StartMain1Command(playerColor))
            Phase.MOVE -> EventBus.getDefault().post(StartMoveCommand(playerColor))
            Phase.MAIN2 -> EventBus.getDefault().post(StartMain2Command(playerColor))
            Phase.END_TURN -> EventBus.getDefault().post(EndTurnCommand(playerColor))
            Phase.END_ROUND -> throw UnsupportedOperationException("END ROUND NEXT?")
        }

    }

}