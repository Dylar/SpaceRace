package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.obtain.ObtainShopCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.getPlayerField
import de.bitb.spacerace.model.objecthandling.getPlayerItems
import de.bitb.spacerace.model.objecthandling.getPlayerPosition
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.Completable
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus.getDefault
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val graphicController: GraphicController,
        private val getPlayerUsecase: GetPlayerUsecase,
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource,
        private var playerColorDispender: PlayerColorDispender
) : ExecuteUseCase<PlayerColor> {

    //TODO clean me
    override fun buildUseCaseCompletable(params: PlayerColor) =
            getPlayerUsecase.buildUseCaseSingle(params)
                    .flatMapCompletable { playerData ->
                        if (canExecute(playerData)) {
                            playerData.nextPhase()
                            val doPhase: (PlayerData) -> PlayerData =
                                    when (playerData.phase) {
                                        Phase.MOVE -> startMove()
                                        Phase.MAIN2 -> startMain2()
                                        Phase.END_TURN -> endTurn()
                                        else -> {
                                            { playerData }
                                        }
                                    }
                            Single.just(doPhase(playerData))
                                    .flatMapCompletable { intoDb -> playerDataSource.insertAll(intoDb) }
                        } else {
                            Completable.complete()
                        }
                    }

    private fun canExecute(playerData: PlayerData) =
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


    private fun startMove(): (PlayerData) -> PlayerData = {
        it.apply { steps.add(graphicController.getPlayerField(fieldController, it.playerColor).gamePosition) }
    }

    private fun startMain2(): (PlayerData) -> PlayerData = {
        obtainField(it)
    }

    private fun endTurn(): (PlayerData) -> PlayerData = {
        it.also {
            graphicController.players
                    .apply {
                        val oldPlayer = this[0]
                        var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
                        forEach {
                            it.getGameImage().zIndex = indexOld++
                        }
                        removeAt(0)
                        add(oldPlayer)

                        Logger.println("oldPlayer: ${oldPlayer.playerColor}")
                        //TODO items in db
                        oldPlayer.playerItems.removeUsedItems()
                    }.let {
                        it.first()
                    }.also { newPlayer ->
                        Logger.println("newPlayer: ${newPlayer.playerColor}")
                        playerColorDispender.publishUpdate(newPlayer.playerColor)
                    }
        }
    }

    private fun obtainField(playerData: PlayerData): PlayerData =
            graphicController
                    .getPlayerField(fieldController, playerData.playerColor)
                    .apply {
                        val items =
                                mutableListOf<Item>().apply { addAll(disposedItems) }
                        items.forEach { it.use(playerData) }
                    }
                    .fieldType
                    .also {
                        when (it) {
                            FieldType.WIN -> obtainWinCommand(playerData)
                            FieldType.LOSE -> obtainLoseCommand(playerData)
                            FieldType.AMBUSH -> obtainAmbushCommand(playerData)
                            FieldType.GIFT -> obtainGiftCommand(playerData)
                            FieldType.MINE -> obtainMineCommand(playerData)
                            FieldType.TUNNEL -> obtainTunnelCommand(playerData)
                            FieldType.GOAL -> obtainGoalCommand(playerData)
                            FieldType.SHOP -> getDefault().post(ObtainShopCommand(playerData))
                            else -> Logger.println("IMPL ME")
                        }
                        Logger.println("Field ${it.name}: $playerData")
                    }
                    .let { playerData }

    private fun obtainGoalCommand(playerData: PlayerData) {
        if (fieldController.currentGoal == graphicController.getPlayerField(fieldController, playerData.playerColor)) {
            playerData.apply {
                credits += GOAL_CREDITS
                victories++
            }
            fieldController.setRandomGoal()
        }
    }

    private fun obtainTunnelCommand(playerData: PlayerData) {
        val tunnel = getRandomTunnel(playerData.playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        graphicController.getPlayer(playerData.playerColor).setFieldPosition(tunnel)
    }

    private fun getRandomTunnel(playerColor: PlayerColor): SpaceField {
        val playerPosition = graphicController.getPlayerPosition(playerColor)
        val tunnelList = fieldController.fieldsMap[FieldType.TUNNEL]!!
        var tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]

        while (tunnel.gamePosition.isPosition(playerPosition)) {
            tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]
        }
        return tunnel
    }

    private fun obtainMineCommand(playerData: PlayerData) {
        (graphicController.getPlayerField(fieldController, playerData.playerColor) as MineField)
                .apply { owner = playerData.playerColor }
    }

    private fun obtainGiftCommand(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor).addRandomGift()
    }

    private fun obtainAmbushCommand(playerData: PlayerData) {
        graphicController.getPlayerItems(playerData.playerColor)
                .attachItem(ItemCollection.SLOW_MINE.create(playerData.playerColor) as DisposableItem)
    }

    private fun obtainLoseCommand(playerData: PlayerData) {
        playerData.substractRandomWin()
    }

    private fun obtainWinCommand(playerData: PlayerData) {
        playerData.addRandomWin()
    }

}