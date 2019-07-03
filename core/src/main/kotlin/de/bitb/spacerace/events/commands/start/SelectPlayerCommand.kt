package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor
import javax.inject.Inject

class SelectPlayerCommand(
        var playerColor: PlayerColor
) : BaseCommand(NONE_PLAYER_DATA) {

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        if (!playerController.gamePlayer.remove(playerColor)) {
            playerController.gamePlayer.add(playerColor)
        }
    }

}