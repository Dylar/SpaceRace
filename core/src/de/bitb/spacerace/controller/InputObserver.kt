package de.bitb.spacerace.controller

import de.bitb.spacerace.events.BaseEvent

interface InputObserver {
    fun <T : BaseEvent> update(event: T)
}