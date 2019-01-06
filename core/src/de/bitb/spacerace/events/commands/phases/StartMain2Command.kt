package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.obtain.*
import de.bitb.spacerace.model.enums.FieldType

class StartMain2Command(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(getPlayerData(game, playerColor))
    }

    override fun execute(game: MainGame) {
        val inputHandler = game.gameController.inputHandler
        when (getPlayerData(game, playerColor).fieldPosition.fieldType) {
            FieldType.WIN -> inputHandler.handleCommand(ObtainWinCommand(playerColor))
            FieldType.LOSE -> inputHandler.handleCommand(ObtainLoseCommand(playerColor))
            FieldType.AMBUSH -> inputHandler.handleCommand(ObtainAmbushCommand(playerColor))
            FieldType.GIFT -> inputHandler.handleCommand(ObtainGiftCommand(playerColor))
            FieldType.MINE -> inputHandler.handleCommand(ObtainMineCommand(playerColor))
            FieldType.TUNNEL -> inputHandler.handleCommand(ObtainTunnelCommand(playerColor))
            FieldType.SHOP -> inputHandler.handleCommand(ObtainShopCommand(playerColor))
            FieldType.GOAL -> inputHandler.handleCommand(ObtainGoalCommand(playerColor))
            else -> {
                Logger.println("IMPL ME")
            }

        }
    }


}