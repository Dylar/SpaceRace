package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class BaseStage(viewport: Viewport = ScreenViewport()) : Stage(viewport) {

    companion object {
        val STAGE_DELEGATE = object : ReadWriteProperty<BaseScreen, BaseStage> {
            var stage: BaseStage? = null
            override fun getValue(thisRef: BaseScreen, property: KProperty<*>) =
                    stage ?: NONE

            override fun setValue(thisRef: BaseScreen, property: KProperty<*>, value: BaseStage) {
                stage = value
            }
        }
        val NONE = object : BaseStage() {}
    }

    val compositDisposable: CompositeDisposable = CompositeDisposable()

    var posX: Float = 0f
    var posY: Float = 0f

    open fun translateBy(distanceX: Float, distanceY: Float) {
        posX += distanceX
        posY += distanceY
    }

    fun translateTo(posX: Float, posY: Float) {
        this.posX = posX
        this.posY = posY
    }

    fun setColor(red: Float = 1f, green: Float = 1F, blue: Float = 1f, alpha: Float = 1f) {
        batch!!.color = Color(red, green, blue, alpha)
    }

    fun clearColor() {
        setColor()
    }

}