package de.bitb.spacerace.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.CameraActionType.*
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.MIN_ZOOM
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.model.objecthandling.GameImage


abstract class BaseScreen(
        val game: MainGame,
        val previousScreen: BaseScreen?
) : Screen {
    companion object {
        var MAIN_DELTA = 0f
    }

//    var currentZoom: Float = 1f
//        set(value) {
//            field = when {
//                value < MIN_ZOOM -> MIN_ZOOM
//                value > MAX_ZOOM -> MAX_ZOOM
//                else -> value
//            }
//        }

    override fun show() {
        val input = mutableListOf<InputProcessor>(*getInputStages().toTypedArray())
        getCameraInput()?.also { input.add(it as InputProcessor) }
        Gdx.input.inputProcessor = InputMultiplexer(*input.toTypedArray(), DebugInputProcessor)
    }

    abstract fun getAllStages(): List<Stage> //guiStage, gameStage
    abstract fun getInputStages(): List<Stage> //guiStage, gameStage
    open fun getCameraInput(): GestureDetector.GestureListener? = null

    override fun render(delta: Float) {
        MAIN_DELTA += delta

        actScreen(delta)
        renderScreen()
        renderCamera()

        if (MAIN_DELTA > 1f) {
            MAIN_DELTA = 0f
        }
    }

    abstract fun actScreen(delta: Float)
    abstract fun renderScreen()
    abstract fun renderCamera()

    open fun getCameraTarget(): GameImage? {
        return null
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        getAllStages().forEach { it.viewport.update(width, height, true) }
    }

    override fun hide() {
        (getAllStages() as? BaseStage)?.disposeDisposables()
    }

    override fun dispose() {
        getAllStages()
                .mapNotNull { it as? BaseStage }
                .forEach { it.dispose() }
    }

}