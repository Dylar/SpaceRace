package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.obtain.ObtainGiftCommand
import de.bitb.spacerace.events.commands.obtain.ObtainLoseCommand
import de.bitb.spacerace.events.commands.obtain.ObtainMineCommand
import de.bitb.spacerace.events.commands.obtain.ObtainWinCommand
import de.bitb.spacerace.model.enums.FieldType

class StartMain2Command(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        return gameController.phaseController.canContinue(gameController.playerController.currentPlayer.playerData)
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val inputHandler = gameController.inputHandler
        val player = gameController.playerController.getPlayer(playerColor)
        when (player.playerData.fieldPosition.fieldType) {
            FieldType.WIN -> inputHandler.handleCommand(ObtainWinCommand(playerColor))
            FieldType.LOSE -> inputHandler.handleCommand(ObtainLoseCommand(playerColor))
            FieldType.GIFT -> inputHandler.handleCommand(ObtainGiftCommand(playerColor))
            FieldType.MINE -> inputHandler.handleCommand(ObtainMineCommand(playerColor))
            FieldType.SHOP -> gameController.phaseController.openShop()
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
        }
    }


}