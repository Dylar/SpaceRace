package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.events.commands.obtain.ObtainGiftCommand
import de.bitb.spacerace.events.commands.obtain.ObtainLoseCommand
import de.bitb.spacerace.events.commands.obtain.ObtainMineCommand
import de.bitb.spacerace.events.commands.obtain.ObtainWinCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.BaseSpace

class StartMain2Command(inputHandler: InputHandler, playerColor: PlayerColor) : PhaseCommand(inputHandler, playerColor) {

    override fun canExecute(space: BaseSpace): Boolean {
        return space.phaseController.canContinue()
    }

    override fun execute(space: BaseSpace) {
        val player = space.playerController.getPlayer(playerColor)
        when (player.playerData.fieldPosition.fieldType) {
            FieldType.WIN -> inputHandler.handleCommand(ObtainWinCommand(playerColor))
            FieldType.LOSE -> inputHandler.handleCommand(ObtainLoseCommand(playerColor))
            FieldType.GIFT -> inputHandler.handleCommand(ObtainGiftCommand(playerColor))
            FieldType.MINE -> inputHandler.handleCommand(ObtainMineCommand(playerColor))
            FieldType.SHOP -> space.phaseController.openShop()
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
        }
    }


}