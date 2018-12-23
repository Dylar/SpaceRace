package de.bitb.spacerace.model.space.control

class ControlToken(val token: String = "NONE") {
    companion object {
        val NONE = ControlToken()
    }
}