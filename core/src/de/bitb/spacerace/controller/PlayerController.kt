package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class PlayerController() {

    var players: MutableList<Player> = ArrayList()
    var playerMap: MutableMap<PlayerColor, Player> = HashMap()

    var currentPlayer: Player = Player.NONE
        get() = if (players.isEmpty()) Player.NONE else players[players.size - 1]

    fun moveTo(spaceField: SpaceField, player: Player) {
        setSteps(player.playerData, spaceField)
//        player.positionData.setPosition(spaceField.positionData)
        player.moveTo(player, spaceField.positionData)
        Logger.println("Player Field: ${spaceField.id}, ${spaceField.fieldType.name}")
    }

    private fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField.positionData)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.positionData)
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
        var indexOld = oldPlayer.image.zIndex + 1 //TODO do it in gui
        for (ship in players) {
            ship.image.zIndex = indexOld--
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