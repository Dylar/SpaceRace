package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand

class ObtainShopCommand(playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {

    }

}