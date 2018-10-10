package de.bitb.spacerace.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.model.Asteroid
import de.bitb.spacerace.model.SpaceField

class GameScreen(game: BaseGame) : BaseScreen(game) {

    private lateinit var asteroid: Asteroid

    override fun show() {
        super.show()
        var spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width - spaceField.x)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width / 2 - spaceField.x / 2)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width - spaceField.x, Gdx.graphics.height - spaceField.y)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width / 2 - spaceField.x / 2, Gdx.graphics.height - spaceField.y)
        spaceField = SpaceField()
        addField(spaceField, posY = Gdx.graphics.height - spaceField.y)
        spaceField = SpaceField()
        addField(spaceField)

        for (actor in stage.actors) {
            Logger.println("ACTOR: X-${actor.x}, Y-${actor.y}")
        }

        asteroid = Asteroid()
        stage.addActor(asteroid)

    }

    private fun addField(spaceField: SpaceField, posX: Float = 0F, posY: Float = 0f) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(getListener(spaceField))
        stage.addActor(spaceField)
    }

    private fun getListener(spaceField: SpaceField): EventListener {
        return object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (asteroid.actions.size == 0) {
//                    var vector1 = Vector2(asteroid.x, asteroid.y)
//                    var vector2 = Vector2(spaceField.x, spaceField.y)
//                    var vector3 = vector2.sub(vector1)
                    var distance = 1//Math.sqrt((vector3.x*vector3.x + vector3.y * vector3.y).toDouble())

                    val move = MoveToAction()
                    move.setPosition(spaceField.x, spaceField.y)
                    move.duration = (GAME_SPEED * distance).toFloat()
                    asteroid.addAction(move)
                }
                return true
            }

        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        var posX = 0f
        var posY = 0f
        val updateCam = when {
            asteroid.actions.size != 0 -> {
                posX = asteroid.x + asteroid.width / 2
                posY = asteroid.y + asteroid.height / 2
                true
            }
            Gdx.input.justTouched() -> {
                posX = Gdx.input.x.toFloat()
                posY = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
                true
            }
            Gdx.input.isTouched -> {
                posX = Gdx.input.x.toFloat()
                posY = Gdx.graphics.height.toFloat() - Gdx.input.y.toFloat()
                true
            }
            else -> false
        }


        if (updateCam) {
            Logger.println((Gdx.input.x > Gdx.graphics.width).toString())
            Logger.println("PosX: $posX, PosY: $posY")
            stage.camera.position.set(posX, posY, 0f)
            stage.camera.update()
        }
    }

    companion object {
        private const val GAME_SPEED_SLOW: Float = 1f
        private const val GAME_SPEED_NORMAL: Float = 5f
        private const val GAME_SPEED_FAST: Float = 10f
        private const val GAME_SPEED: Float = GAME_SPEED_FAST
        private const val GAME_TICK: Float = 10f
    }
}
