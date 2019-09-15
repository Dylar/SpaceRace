package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.ObtainShopEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.game.action.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseCommand(playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var nextPhaseUsecase: NextPhaseUsecase

    @Inject
    lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        nextPhaseUsecase.getResult(
                params = DONT_USE_THIS_PLAYER_DATA.playerColor,
                onSuccess = ::setGraphics)
    }

    private fun setGraphics(nextPhaseResult: NextPhaseResult) {
        val player = nextPhaseResult.player
        val position = player.gamePosition
        val phase = player.phase

        when (nextPhaseResult) {
            is ObtainFieldResult,
            is StartMoveResult -> graphicController.setConnectionColor(nextPhaseResult.player, nextPhaseResult.targetableFields)
        }

        when (nextPhaseResult) {
            is ObtainShopResult -> EventBus.getDefault().post(ObtainShopEvent(nextPhaseResult.player.playerColor))
            is ObtainMineResult -> graphicController.setMineOwner(player)
            is ObtainGoalResult -> graphicController.setGoal(position, nextPhaseResult.newGoal.gamePosition)
            is ObtainTunnelResult -> graphicController.teleportPlayer(player.playerColor, player.gamePosition)
        }

        when (phase) {
            Phase.END_TURN -> graphicController.changePlayer()
            else -> {
            }
        }
    }

}