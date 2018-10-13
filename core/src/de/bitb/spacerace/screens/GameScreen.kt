package de.bitb.spacerace.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.core.LineRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.*


class GameScreen(game: BaseGame) : BaseScreen(game) {

    private var space: Space = Space()

    private lateinit var currentShip: Label
    private lateinit var diceResult: Label

    override fun show() {
        super.show()
        createBackground()
        createSpace()
        createUi()
    }

    private fun createSpace() {
        for (spaceField in space.fields) {
            gameStage.addActor(spaceField)
        }
        for (ship in space.ships) {
            gameStage.addActor(ship)
        }
    }

    private fun createUi() {
        val skin = Skin(Gdx.files.internal("uiskin.json"))

        val diceBtn = TextButton("Dice", skin, "default")
        diceBtn.width += 40f
        diceBtn.setPosition(Gdx.graphics.width - diceBtn.width, diceBtn.height * 2)
        diceBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.dice()
                return true
            }
        })

        val followBtn = TextButton("Lock", skin, "default")
        followBtn.width = diceBtn.width
        followBtn.setPosition(Gdx.graphics.width - followBtn.width, followBtn.height)
        followBtn.color = Color.ROYAL
        followBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                followBtn.color = if (followBtn.color == Color.FOREST) Color.ROYAL else Color.FOREST
                lockCamera(space.getCurrentShip())
                return true
            }
        })

        val labelColor = Pixmap((followBtn.width).toInt(), (followBtn.height * 5).toInt(), Pixmap.Format.RGB888);
        labelColor.setColor(Color.CYAN)
        labelColor.fill()
        val background = Image(Texture(labelColor))
        background.setPosition(Gdx.graphics.width - followBtn.width, 0f)

        diceResult = Label("0", skin, "default")
        diceResult.width = followBtn.width
        diceResult.setPosition(Gdx.graphics.width - followBtn.width / 2 - diceResult.width / 2, followBtn.height * 3)
        diceResult.style.fontColor = Color.RED

        currentShip = Label("-", skin, "default")
        currentShip.width = followBtn.width
        currentShip.setPosition(Gdx.graphics.width - followBtn.width, followBtn.height * 4)
        currentShip.style.fontColor = Color.RED

        guiStage.addActor(background)
        guiStage.addActor(diceResult)
        guiStage.addActor(currentShip)
        guiStage.addActor(diceBtn)
        guiStage.addActor(followBtn)
    }

    private fun createBackground() {
        val background = Background()
        background.width = backgroundStage.width
        background.height = backgroundStage.height
        backgroundStage.addActor(background)
    }

    override fun renderGame(delta: Float) {
        for (spaceField1 in space.fields) {
            for (spaceField2 in spaceField1.connections) {
                val start = Vector2(spaceField1.x + spaceField1.width / 2, spaceField1.y + spaceField1.height / 2)
                val end = Vector2(spaceField2.x + spaceField2.width / 2, spaceField2.y + spaceField2.height / 2)
                LineRenderer.DrawDebugLine(start, end, 10, Color.GREEN, gameStage.camera.combined)
            }
        }
        super.renderGame(delta)
    }

    override fun renderGui(delta: Float) {
        diceResult.setText("${(space.diceResult - space.steps.size)}/${(space.diceResult - 1)}")
        currentShip.setText(space.getCurrentShip().color.toString())
        super.renderGui(delta)
    }
}
