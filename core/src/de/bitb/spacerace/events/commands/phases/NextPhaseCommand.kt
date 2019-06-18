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
                Phase.END_ROUND -> true
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
        val doPhase: (PlayerData) -> Single<PlayerData>  =
                when (playerData.phase) {
                    Phase.MAIN1 -> {
                        { Single.just(playerData) }
                    }
                    Phase.MOVE -> startMove(game)
                    Phase.MAIN2 -> startMain2(game)
                    Phase.END_TURN -> endTurn()
                }

        nextPhaseUsecase.execute(Pair(playerData, doPhase))

    }

    private fun startMove(game: MainGame): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.apply { steps.add(getPlayerField(game, it.playerColor).gamePosition) })
    }

    private fun startMain2(game: MainGame): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also { playerData ->
            val field = getPlayerField(game, playerData.playerColor)
            field.disposedItems.forEach { it.use(game, playerData) }

            val command: BaseCommand = when (field.fieldType) {
                FieldType.WIN -> ObtainWinCommand(playerData)
                FieldType.LOSE -> ObtainLoseCommand(playerData)
                FieldType.AMBUSH -> ObtainAmbushCommand(playerData)
                FieldType.GIFT -> ObtainGiftCommand(playerData)
                FieldType.MINE -> ObtainMineCommand(playerData)
                FieldType.TUNNEL -> ObtainTunnelCommand(playerData)
                FieldType.SHOP -> ObtainShopCommand(playerData)
                FieldType.GOAL -> ObtainGoalCommand(playerData)
                else -> {
                    Logger.println("IMPL ME")
                    ObtainLoseCommand(playerData)
                }
            }

//            FieldType.PLANET -> TODO()
//            FieldType.RANDOM -> TODO()
//            FieldType.UNKNOWN -> TODO()
            EventBus.getDefault().post(command)
        })
    }

    private fun endTurn(): (PlayerData) -> Single<PlayerData> = {
        Single.just(it.also {
            playerController.nextPlayer()
//            publish current player ?
        })
    }

}