package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.ObtainShopEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandPool.getCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.game.action.*
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseCommand : BaseCommand() {

    companion object {
        fun get(player: PlayerData) =
                getCommand(NextPhaseCommand::class)
                        .also { it.DONT_USE_THIS_PLAYER_DATA = player }
    }

    @Inject
    protected lateinit var nextPhaseUsecase: NextPhaseUsecase

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        compositDisposable += nextPhaseUsecase.getResult(
                params = DONT_USE_THIS_PLAYER_DATA.playerColor,
                onSuccess = {
                    setGraphics(it)
                    reset()
                },
                onError = {
                    it.printStackTrace()
                    reset()
                })
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

        if (nextPhaseResult.triggeredItems.isNotEmpty()) attachToPlayer(nextPhaseResult)

        when (phase) {
            Phase.END_TURN -> graphicController.changePlayer()
            else -> {
            }
        }
    }

    private fun attachToPlayer(result: NextPhaseResult) {
        val fieldGraphic = graphicController.getFieldGraphic(result.player.positionField.target.gamePosition)
        val itemGraphic = fieldGraphic.disposedItems.first { it.itemType == result.triggeredItems.first().itemInfo.type }
        val playerGraphic = graphicController.getPlayerGraphic(result.player.playerColor)
        fieldGraphic.removeItem(itemGraphic)
        itemGraphic.itemImage.setRotating(itemGraphic, playerGraphic.playerImage, playerGraphic.playerImage.width * 0.7)
    }

}