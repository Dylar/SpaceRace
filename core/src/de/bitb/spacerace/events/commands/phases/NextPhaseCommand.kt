package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.game.NextPhaseUsecase
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NextPhaseCommand(playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var nextPhaseUsecase: NextPhaseUsecase

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame) =
            when (playerData.phase) {
                Phase.MAIN1 -> canEndMain1(playerData)
                Phase.MOVE -> canEndMove(playerData)
                Phase.MAIN2 -> canEndMain2(playerData)
                Phase.END_TURN -> playerData.phase.isEndTurn()
                Phase.END_ROUND -> false
            }

    private fun canEndMain1(playerData: PlayerData): Boolean {
        return playerData.phase.isMain1() && playerController.areStepsLeft(playerData)
    }

    private fun canEndMove(playerData: PlayerData): Boolean {
        return !playerController.canMove(playerData)
    }

    private fun canEndMain2(playerData: PlayerData): Boolean {
        return playerData.phase.isMain2()
    }


    override fun execute(game: MainGame) {
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")

        nextPhaseUsecase.execute(playerData)

    }


}