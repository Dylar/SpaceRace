package de.bitb.spacerace.controller

class ControlToken(val token: String = "NONE_PLAYER") {
    companion object {
        val NONE = ControlToken()
    }
}