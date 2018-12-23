package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.events.commands.phases.EndRoundCommand
import de.bitb.spacerace.events.commands.phases.EndTurnCommand
import de.bitb.spacerace.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerController(val space: BaseSpace, val inputHandler: InputHandler) {

    var currentPlayer: Player = Player.NONE
        get() = if (players.isEmpty()) Player.NONE else players[players.size - 1]
    var players: MutableList<Player> = ArrayList()

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
        val playerData = currentPlayer.playerData
        return playerData.phase.isMain1() &&
                playerData.playerColor == playerColor &&
                !playerData.diced
    }

    fun dice(maxResult: Int = 6) {
        val playerData = currentPlayer.playerData
        playerData.diced = true

        playerData.steps.add(playerData.fieldPosition)
        playerData.diceResult += (Math.random() * maxResult).toInt() + 1

        Logger.println("DiceResult: ${playerData.diceResult}")
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
        Logger.println("nextTurn1")
        val oldPlayer = players[0]
        var indexOld = oldPlayer.zIndex + 1 //TODO do it in gui
        for (ship in players) {
            ship.zIndex = indexOld--
        }
        players.add(oldPlayer)
        players.removeAt(0)

        Logger.println("nextTurn2: ${currentPlayer.playerData.phase}")
        if (space.playerController.isRoundEnd()) {
            inputHandler.handleCommand(EndRoundCommand(inputHandler))
        }
    }

    fun nextRound() {
        Logger.println("nextRound1")
        space.fieldController.harvestOres()

        for (player in players) {
            val playerData = player.playerData
//            val saveData = playerData.copy() //TODO save me
            playerData.steps = ArrayList()
            playerData.diceResult = 0
            playerData.diced = false
            playerData.phase = Phase.MAIN1
            Logger.println("nextRound Phase: ${playerData.phase}")
        }

        Logger.println("nextRound2")
    }

    fun getPlayer(playerColor: PlayerColor): Player {
        for (player in players) {
            if (playerColor == player.playerData.playerColor) {
                return player
            }
        }
        return Player.NONE
    }
}