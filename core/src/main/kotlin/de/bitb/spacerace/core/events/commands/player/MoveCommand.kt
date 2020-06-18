package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool.getCommand
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import io.reactivex.rxjava3.kotlin.plusAssign
import javax.inject.Inject

class MoveCommand : BaseCommand() {
    companion object {
        fun get(targetPosition: PositionData,
                player: PlayerColor
        ) = getCommand(MoveCommand::class)
                .also {
                    it.targetPosition = targetPosition
                    it.player = player
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

    override fun execute() {
        compositeDisposable += moveUsecase.getResult(
                params = player to targetPosition,
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
    }
}