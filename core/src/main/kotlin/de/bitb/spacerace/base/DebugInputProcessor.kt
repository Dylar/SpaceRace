package de.bitb.spacerace.base

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import de.bitb.spacerace.core.utils.Logger

object DebugInputProcessor : InputProcessor {
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        Logger.justPrint("KEY DOWN: ${Input.Keys.toString(keycode)}, KEY CODE: $keycode")
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

}