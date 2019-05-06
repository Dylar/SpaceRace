package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class StartMain2Command(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val field = getPlayerField(game, playerColor)
        field.disposedItems.forEach { it.use(game, playerColor) }

        val command: BaseCommand = when (field.fieldType) {
            FieldType.WIN -> ObtainWinCommand(playerColor)
            FieldType.LOSE -> ObtainLoseCommand(playerColor)
            FieldType.AMBUSH -> ObtainAmbushCommand(playerColor)
            FieldType.GIFT -> ObtainGiftCommand(playerColor)
            FieldType.MINE -> ObtainMineCommand(playerColor)
            FieldType.TUNNEL -> ObtainTunnelCommand(playerColor)
            FieldType.SHOP -> ObtainShopCommand(playerColor)
            FieldType.GOAL -> ObtainGoalCommand(playerColor)
            else -> {
                Logger.println("IMPL ME")
                ObtainLoseCommand(playerColor)
            }
        }

//            FieldType.PLANET -> TODO()
//            FieldType.RANDOM -> TODO()
//            FieldType.UNKNOWN -> TODO()
        EventBus.getDefault().post(command)
    }
}
