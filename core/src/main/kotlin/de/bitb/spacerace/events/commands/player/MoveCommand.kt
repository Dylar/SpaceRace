package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandPool.getCommand
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.usecase.game.action.MoveResult
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class MoveCommand : BaseCommand() {
    companion object {
        fun get(targetPosition: PositionData,
                playerData: PlayerData
        ) = getCommand(MoveCommand::class)
                .also {
                    it.targetPosition = targetPosition
                    it.DONT_USE_THIS_PLAYER_DATA = playerData
                }
    }

    @Inject
    protected lateinit var moveUsecase: MoveUsecase

    @Inject
    protected lateinit var graphicController: GraphicController

    private lateinit var targetPosition: PositionData

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean = true

    override fun execute() {
        compositDisposable += moveUsecase.getResult(
                params = DONT_USE_THIS_PLAYER_DATA.playerColor to targetPosition,
                onSuccess = {
                    setGraphics(it)
                    reset()
                },
                onError = { reset() }
        )
    }

    private fun setGraphics(moveResult: MoveResult) {
        graphicController.movePlayer(moveResult)
        graphicController.setConnectionColor(moveResult.player, moveResult.targetableFields)
//        graphicController.setConnectionColor(moveInfo.toConnectionInfo())
    }
}