package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.events.commands.EndRoundCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerController(val space: BaseSpace, val inputHandler: InputHandler) {

    var firstPlayer: Player = Player()
    var currentPlayer: Player = firstPlayer
        get() = if (players.isEmpty()) firstPlayer else players[players.size - 1]
    var players: MutableList<Player> = ArrayList()

    init {
//        space.history.nextRound(currentPlayer) //TODO yeah?
    }

    fun moveTo(player: Player = currentPlayer, spaceField: SpaceField) {
        val playerData = player.playerData

        setSteps(playerData, spaceField)

        playerData.fieldPosition = spaceField
        player.moveTo(spaceField)
        Logger.println("Player Field: ${playerData.fieldPosition.id}, ${playerData.fieldPosition.fieldType.name}")

    }

    private fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = space.playerController.previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField)
        }
    }

    fun previousFieldSelected(playerData: PlayerData = currentPlayer.playerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep == spaceField
    }

    fun canDice(playerColor: PlayerColor): Boolean {
        return currentPlayer.playerColor == playerColor && currentPlayer.playerData.diced
    }

    fun dice(maxResult: Int = 6) {
        val playerData = currentPlayer.playerData
        if (playerData.diceResult - playerData.steps.size <= 0 && !playerData.diced && playerData.phase.isMain1()) {
            playerData.diced = true

            playerData.steps.add(playerData.fieldPosition)
            playerData.diceResult += (Math.random() * maxResult).toInt() + 1

            Logger.println("DiceResult: ${playerData.diceResult}")
        }
    }

    fun isRoundEnd(): Boolean {
        for (player in players) {
            if (player.playerData.phase != Phase.END_TURN) {
                return false
            }
        }
        return true
    }

    fun stepsLeft(playerData: PlayerData = currentPlayer.playerData): Int {
        return playerData.diceResult - (playerData.steps.size - 1)
    }

    fun nextTurn() {
        val playerData = currentPlayer.playerData
        //TODO save das dann History oder so

        val oldPlayer = players[0]
        var indexOld = oldPlayer.zIndex + 1 //TODO do it in gui
        for (ship in players) {
            ship.zIndex = indexOld--
        }
        players.add(oldPlayer)
        players.removeAt(0)

//        space.history.setSteps(playerData.steps)

        if (space.playerController.isRoundEnd()) {
            inputHandler.handleCommand(EndRoundCommand())
        }
    }

    fun nextRound() {
//        space.history.nextRound(space.playerController.currentPlayer) //TODO false ....

        space.fieldController.harvestOres() //TODO in player con?

        for (player in players) {
            val playerData = player.playerData
//            val saveData = playerData.copy() //TODO save me
            playerData.steps = ArrayList()
            playerData.diceResult = 0
            playerData.diced = false
            playerData.phase = Phase.next(playerData.phase)
        }
    }
}