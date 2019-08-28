package de.bitb.spacerace.controller

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private val graphicController: GraphicController
) {

    var currentPlayerData = NONE_PLAYER_DATA

    fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            graphicController.getPlayer(playerColor).playerItems

}