package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.toConnectionInfo
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.game.action.NextPhaseResult
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
import de.bitb.spacerace.usecase.game.action.ObtainFieldResult
import de.bitb.spacerace.usecase.game.action.ObtainGoalResult
import javax.inject.Inject

class NextPhaseCommand(playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var nextPhaseUsecase: NextPhaseUsecase

    @Inject
    lateinit var fieldController: FieldController

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
        val position = nextPhaseResult.player.gamePosition
        val phase = nextPhaseResult.player.phase

        if (nextPhaseResult is ObtainFieldResult){
            fieldController.setConnectionColor(nextPhaseResult.toConnectionInfo(position))
        }

        if (nextPhaseResult is ObtainGoalResult) {
            graphicController.setGoal(position, nextPhaseResult.newGoal.gamePosition)
        }

        when (phase) {
            Phase.END_TURN -> graphicController.changePlayer()
            else -> {
            }
        }
    }

}