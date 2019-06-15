package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class StartMain2Command(playerData: PlayerData) : PhaseCommand(playerData) {

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
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
    }
}
