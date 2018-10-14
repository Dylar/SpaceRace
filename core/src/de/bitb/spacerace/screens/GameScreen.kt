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

        val followBtn = TextButton("Lock", skin, "default")
        followBtn.width = 120f
        followBtn.setPosition(Gdx.graphics.width - followBtn.width, followBtn.height * 5)
        followBtn.color = Color.ROYAL
        followBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                followBtn.color = if (followBtn.color == Color.FOREST) Color.ROYAL else Color.FOREST
                lockCamera(space.getCurrentShip())
                return true
            }
        })

        val diceBtn = TextButton("Dice", skin, "default")
        diceBtn.width = followBtn.width
        diceBtn.setPosition(Gdx.graphics.width - diceBtn.width, diceBtn.height * 2)
        diceBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.dice()
                return true
            }
        })

        val zoomPlusBtn = TextButton("+", skin, "default")
        zoomPlusBtn.width = followBtn.width
        zoomPlusBtn.setPosition(Gdx.graphics.width - zoomPlusBtn.width, zoomPlusBtn.height * 3)
        zoomPlusBtn.color = Color.ROYAL
        zoomPlusBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                currentZoom--
                zoom()
                return true
            }
        })

        val zoomMinusBtn = TextButton("-", skin, "default")
        zoomMinusBtn.width = followBtn.width
        zoomMinusBtn.setPosition(Gdx.graphics.width - zoomMinusBtn.width, zoomMinusBtn.height * 4)
        zoomMinusBtn.color = Color.ROYAL
        zoomMinusBtn.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                currentZoom++
                zoom()
                return true
            }
        })

        val labelColor = Pixmap((followBtn.width).toInt(), (zoomMinusBtn.height * 6).toInt(), Pixmap.Format.RGB888);
        labelColor.setColor(Color.CYAN)
        labelColor.fill()
        val background = Image(Texture(labelColor))
        background.setPosition(Gdx.graphics.width - background.width, 0f)

        diceResult = Label("0", skin, "default")
        diceResult.width = zoomMinusBtn.width
        diceResult.setPosition(Gdx.graphics.width - diceResult.width / 2 - diceResult.width / 2, 0f)
        diceResult.style.fontColor = Color.RED

        currentShip = Label("-", skin, "default")
        currentShip.width = zoomMinusBtn.width
        currentShip.setPosition(Gdx.graphics.width - currentShip.width, zoomMinusBtn.height)
        currentShip.style.fontColor = Color.RED

        guiStage.addActor(background)
        guiStage.addActor(diceResult)
        guiStage.addActor(followBtn)
        guiStage.addActor(currentShip)
        guiStage.addActor(diceBtn)
        guiStage.addActor(zoomMinusBtn)
        guiStage.addActor(zoomPlusBtn)
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
                drawConnection(spaceField1, spaceField2, Color.RED)
            }
        }

        val ship = space.getCurrentShip()
        if (space.stepsLeft() == 0 && space.steps.size > 1) {
            drawConnection(ship.fieldPosition, space.getPreviousStep(), Color.GREEN)
        } else {
            for (spaceField2 in ship.fieldPosition.connections) {
                drawConnection(ship.fieldPosition, spaceField2, Color.GREEN)
            }
        }

        super.renderGame(delta)
        val batch = gameStage.batch
        batch.begin()
        ship.draw(batch, 0f)
        batch.end()

        cameraTarget = ship
    }

    private fun drawConnection(spaceField1: SpaceField, spaceField2: SpaceField, color: Color) {
        val start = Vector2(spaceField1.x + spaceField1.width / 2, spaceField1.y + spaceField1.height / 2)
        val end = Vector2(spaceField2.x + spaceField2.width / 2, spaceField2.y + spaceField2.height / 2)
        LineRenderer.DrawDebugLine(start, end, 10, color, gameStage.camera.combined)
    }

    override fun renderGui(delta: Float) {
        diceResult.setText("${(space.stepsLeft())}/${(space.diceResult - 1)}")
        currentShip.setText(space.getCurrentShip().color.toString())
        super.renderGui(delta)
    }
}
