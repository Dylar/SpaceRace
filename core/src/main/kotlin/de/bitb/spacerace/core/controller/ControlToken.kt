package de.bitb.spacerace.core.controller

class ControlToken(val token: String = "NONE_PLAYER") {
    companion object {
        val NONE = ControlToken()
    }
}