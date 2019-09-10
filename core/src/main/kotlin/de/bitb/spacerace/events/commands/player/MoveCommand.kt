package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.MoveInfo
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import javax.inject.Inject

class MoveCommand(
        private val targetPosition: PositionData,
        playerData: PlayerData
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var moveUsecase: MoveUsecase

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean = true

    override fun execute() {
        moveUsecase.getResult(
                params = DONT_USE_THIS_PLAYER_DATA.playerColor to targetPosition,
                onSuccess = ::setGraphics
        )
    }

    private fun setGraphics(moveInfo: MoveInfo) {
        graphicController.movePlayer(moveInfo)
//        graphicController.setConnectionColor(moveInfo.toConnectionInfo())
    }
}