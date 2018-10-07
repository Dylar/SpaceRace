package de.bitb.spacerace.screens

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.DrawList
import de.bitb.spacerace.model.Asteroid
import de.bitb.spacerace.model.Field
import de.bitb.spacerace.model.SpaceField

class GameScreen(game: BaseGame) : BaseScreen(game) {

    private var spaceFields: DrawList = DrawList()
    private lateinit var asteroid: Asteroid

    override fun show() {


        var spaceField = SpaceField()
        spaceField.moveTo(0f, 0f)
        spaceFields.add(spaceField)

        spaceField = SpaceField()
        spaceField.moveTo(0f, (Gdx.graphics.height / 2 - spaceField.img.height / 2).toFloat())
        spaceFields.add(spaceField)

        spaceField = SpaceField()
        spaceField.moveTo(0f, (Gdx.graphics.height - spaceField.img.height).toFloat())
        spaceFields.add(spaceField)

        asteroid = Asteroid()
    }

    private var timer: Float = 0f
    private var speed: Float = 0.1f

    override fun render(delta: Float) {
        super.render(delta)

        timer += delta * GAME_SPEED
        if (timer < GAME_TICK) {
            timer = 0f
            update()
        }

        if (Gdx.input.justTouched()) {
            val pointer = Field()
            pointer.setAsInput()
            val field = spaceFields.collide(pointer)
            if (field != pointer) {
                asteroid.moveTo(field)
            }
            speed += 0.01f
        }

        spaceFields.draw(game.batch)
        asteroid.draw(game.batch)

        game.batch.end()

    }

    private fun update() {
//        spaceFields.move(40f * speed, 20f * speed)

        if (asteroid.field.posX + asteroid.img.width > Gdx.graphics.width) {
            game.screen = GameOverScreen(game)
            dispose()
        } else {
            asteroid.move(40f * speed)

        }
    }

    companion object {
        private const val GAME_SPEED_SLOW: Float = 100f
        private const val GAME_SPEED_NORMAL: Float = 1f
        private const val GAME_SPEED_FAST: Float = 0.01f
        private const val GAME_SPEED: Float = GAME_SPEED_FAST
        private const val GAME_TICK: Float = 1f
    }
}
