package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType

class StartMain2Command(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        return canContinue(gameController.playerController.currentPlayer.playerData)
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val inputHandler = gameController.inputHandler
        val player = gameController.playerController.getPlayer(playerColor)
        Logger.println("OBTAIN: ${player.playerData.fieldPosition.fieldType}")
        when (player.playerData.fieldPosition.fieldType) {
            FieldType.WIN -> inputHandler.handleCommand(ObtainWinCommand(playerColor))
            FieldType.LOSE -> inputHandler.handleCommand(ObtainLoseCommand(playerColor))
            FieldType.GIFT -> inputHandler.handleCommand(ObtainGiftCommand(playerColor))
            FieldType.MINE -> inputHandler.handleCommand(ObtainMineCommand(playerColor))
            FieldType.TUNNEL -> inputHandler.handleCommand(ObtainTunnelCommand(playerColor))
            FieldType.SHOP -> inputHandler.handleCommand(ObtainShopCommand(playerColor))
            else -> {
                Logger.println("IMPL ME")
            }

        }
    }


}