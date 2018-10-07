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
        spaceField.moveTo(spaceField.img.width.toFloat(), spaceField.img.height.toFloat())
        spaceFields.add(spaceField)

        spaceField = SpaceField()
        spaceField.moveTo(spaceField.img.width.toFloat() * 2, spaceField.img.height.toFloat() * 2)
        spaceFields.add(spaceField)

        asteroid = Asteroid()
    }

    private var timer: Float = 0f

    override fun render(delta: Float) {
        super.render(delta)

        timer += delta * GAME_SPEED
        if (timer > GAME_SPEED) {
            timer = 0f
            update()
        }

        if (Gdx.input.isTouched) {
            val pointer = Field()
            pointer.input(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
            val field = spaceFields.collide(pointer)
            if (field != pointer) {
                asteroid.moveTo(field)
            }
        }

        spaceFields.draw(game.batch)

        asteroid.draw(game.batch)

        game.batch.end()

    }

    private fun update() {
        spaceFields.move(40f, 20f)

        if (asteroid.field.posX + asteroid.img.width > Gdx.graphics.width) {
            game.screen = GameOverScreen(game)
            dispose()
        } else {
            asteroid.move(40f)

        }
    }

    companion object {
        private const val GAME_SPEED: Float = 1f
    }
}
