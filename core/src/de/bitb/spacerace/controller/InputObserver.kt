package de.bitb.spacerace.controller

import de.bitb.spacerace.model.events.BaseEvent

interface InputObserver {
    fun <T : BaseEvent> update(event: T)
}