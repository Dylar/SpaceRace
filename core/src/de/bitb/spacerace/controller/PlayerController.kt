package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerController() {

    var players: MutableList<Player> = ArrayList()
    var playerMap: MutableMap<PlayerColor, Player> = HashMap()

    var currentPlayer: Player = Player.NONE
        get() = if (players.isEmpty()) Player.NONE else players[players.size - 1]

    fun moveTo(spaceField: SpaceField, playerData: PlayerData) {
        setSteps(playerData, spaceField)
        playerData.fieldPosition = spaceField
        getPlayer(playerData.playerColor).moveTo(spaceField)
        Logger.println("Player Field: ${playerData.fieldPosition.id}, ${playerData.fieldPosition.fieldType.name}")
    }

    private fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep == spaceField
    }

    fun isRoundEnd(): Boolean {
        for (player in players) {
            if (player.playerData.phase != Phase.END_TURN) {
                return false
            }
        }
        return true
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

        oldPlayer.playerData.playerItems.removeUsedItems()
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