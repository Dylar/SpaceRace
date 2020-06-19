package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.bitb.spacerace.usecase.DisposableHandler
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseStage(
        viewport: Viewport = ScreenViewport()
) : Stage(viewport), DisposableHandler {

    companion object {
        val NONE_STAGE = object : BaseStage() {}
    }

    override val compositeDisposable = CompositeDisposable()

    fun setColor(red: Float = 1f, green: Float = 1F, blue: Float = 1f, alpha: Float = 1f) {
        batch!!.color = Color(red, green, blue, alpha)
    }

    fun clearColor() {
        setColor()
    }

}