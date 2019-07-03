package de.bitb.spacerace.controller

import de.bitb.spacerace.events.commands.BaseCommand

interface InputObserver {
    fun <T : BaseCommand> update(event: T)
}