package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.MoveInfo
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import javax.inject.Inject

class MoveCommand(
        val spaceField: SpaceField,
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
                params = playerData.playerColor to spaceField,
                onSuccess = ::setGraphics
        )
    }

    private fun setGraphics(moveInfo: MoveInfo) {
        graphicController.movePlayer(moveInfo)
//        graphicController.setConnectionColor(moveInfo.toConnectionInfo())
    }
}