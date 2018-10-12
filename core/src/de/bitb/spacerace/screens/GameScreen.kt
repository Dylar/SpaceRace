package de.bitb.spacerace.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.model.Background
import de.bitb.spacerace.model.Button
import de.bitb.spacerace.model.Ship
import de.bitb.spacerace.model.SpaceField

class GameScreen(game: BaseGame) : BaseScreen(game) {

    private lateinit var ship: Ship

    override fun show() {
        super.show()
        createBackground()
        createField()
        createPlayer()
        createUi()
    }

    private fun createUi() {
        val moveCenterBtn = Button()
        moveCenterBtn.setPosition(Gdx.graphics.width - moveCenterBtn.width, 0f)
        moveCenterBtn.color = Color.CHARTREUSE
        moveCenterBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                ship.moveTo(gameStage.width / 2, gameStage.height / 2, 1f, 1f)
                return true
            }
        })
        guiStage.addActor(moveCenterBtn)

        val followBtn = Button()
        followBtn.setPosition(Gdx.graphics.width - followBtn.width, followBtn.height)
        followBtn.color = Color.FOREST
        followBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                lockCamera(ship)
                return true
            }
        })
        guiStage.addActor(followBtn)
    }

    private fun createPlayer() {
        ship = Ship()
        gameStage.addActor(ship)
    }

    private fun createField() {
        var spaceField = SpaceField()
        addField(spaceField)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width - spaceField.width)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width / 2 - spaceField.width / 2)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width - spaceField.width, Gdx.graphics.height - spaceField.height)
        spaceField = SpaceField()
        addField(spaceField, Gdx.graphics.width / 2 - spaceField.width / 2, Gdx.graphics.height - spaceField.height)
        spaceField = SpaceField()
        addField(spaceField, posY = Gdx.graphics.height - spaceField.height)
    }

    private fun addField(spaceField: SpaceField, posX: Float = 0F, posY: Float = 0f) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                ship.moveTo(spaceField)
                return true
            }
        })
        gameStage.addActor(spaceField)
    }

    private fun createBackground() {
        val background = Background()
        background.width = gameStage.width
        background.height = gameStage.height
        gameStage.addActor(background)
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

}
