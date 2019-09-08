package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.NextPhaseInfo
import de.bitb.spacerace.controller.toConnectionInfo
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.getPlayerPosition
import de.bitb.spacerace.usecase.game.action.NextPhaseUsecase
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
                params = playerData.playerColor,
                onSuccess = ::setGraphics)
    }

    private fun setGraphics(nextPhaseInfo: NextPhaseInfo) {
        val position = graphicController.getPlayerPosition(nextPhaseInfo.playerData.playerColor)
        fieldController.setConnectionColor(nextPhaseInfo.toConnectionInfo(position))

        when (nextPhaseInfo.phase) {
            Phase.END_TURN -> graphicController.changePlayer()
            else -> {
            }
        }
    }

}