package de.bitb.spacerace.controller

class ControlToken(val token: String = "NONE") {
    companion object {
        val NONE = ControlToken()
    }
}