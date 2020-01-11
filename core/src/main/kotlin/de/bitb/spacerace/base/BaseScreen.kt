package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.core.MainGame

abstract class BaseScreen(
        val game: MainGame,
        val previousScreen: BaseScreen?
) : Screen {
    companion object {
        var MAIN_DELTA = 0f
    }

    abstract var allStages: List<Stage>
    abstract var inputStages: List<Stage>
    protected var cameraInput: GestureDetector.GestureListener? = null

    override fun show() {
        val input = mutableListOf<InputProcessor>(*inputStages.toTypedArray())
        cameraInput?.also { input.add(GestureDetector(it)) }
        Gdx.input.inputProcessor = InputMultiplexer(*input.toTypedArray(), DebugInputProcessor)
    }

    override fun render(delta: Float) {
        MAIN_DELTA += delta

        allStages.forEach { it.act(delta) }
        allStages.forEach { it.draw() }
        if (this is CameraRenderer) renderCamera()

        if (MAIN_DELTA > 1f) {
            MAIN_DELTA = 0f
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        allStages.forEach { it.viewport.update(width, height, true) }
    }

    override fun hide() {
        allStages.mapNotNull { it as? BaseStage }
                .forEach { it.disposeDisposables() }
    }

    override fun dispose() {
        allStages
                .mapNotNull { it as? BaseStage }
                .forEach { it.dispose() }
    }

}