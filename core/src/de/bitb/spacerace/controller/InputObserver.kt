package de.bitb.spacerace.controller

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

interface InputObserver {
    fun <T : BaseCommand> update(game: MainGame, event: T)
}