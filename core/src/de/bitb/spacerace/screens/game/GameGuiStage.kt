package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace

class GameGuiStage(val space: BaseSpace, screen: GameScreen) : BaseStage() {

    private var currentShip: Label
    private var diceResult: Label
    private var currentPhase: Label

    init {
        val skin = Skin(Gdx.files.internal("uiskin.json"))

        val padding = 1.2f
        val slotHeight = 32f
        val slotWidth = 120f
        val guiHeight = slotHeight * 2 * padding
        val guiWidth = slotWidth * 4 * padding + slotWidth * 0.2f
        val guiPosX = Gdx.graphics.width - guiWidth

        val background = Image(TextureCollection.background)
        background.width = guiWidth
        background.height = guiHeight
        background.setPosition(guiPosX, 0f)
        addActor(background)

        var posX = Gdx.graphics.width - slotWidth * padding
        var posY = 0f

        val phaseBtn = createButton(skin, "Phase", slotWidth, posX, posY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.nextPhase()
                return true
            }
        })
        addActor(phaseBtn)

        posY += slotHeight
        currentPhase = createLabel(skin, "-", slotWidth, posX, posY)
        addActor(currentPhase)

        posX -= slotWidth * padding
        posY = 0f
        val diceBtn = createButton(skin, "Dice", slotWidth, posX, posY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.dice()
                return true
            }
        })
        addActor(diceBtn)

        posY += slotHeight
        diceResult = createLabel(skin, "-", slotWidth, posX, posY)
        addActor(diceResult)

        posX -= slotWidth * padding
        posY = 0f
        val zoomPlusBtn = createButton(skin, "+", slotWidth, posX, posY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.currentZoom--
                screen.zoom()
                return true
            }
        })
        addActor(zoomPlusBtn)

        posY += slotHeight
        val zoomMinusBtn = createButton(skin, "-", slotWidth, posX, posY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.currentZoom++
                screen.zoom()
                return true
            }
        })
        addActor(zoomMinusBtn)

        posX -= slotWidth * padding
        posY = 0f
        val followBtn = createButton(skin, "Lock", slotWidth, posX, posY, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.lockCamera()
                return true
            }
        })
        addActor(followBtn)

        posY += slotHeight
        currentShip = createLabel(skin, "-", slotWidth, posX, posY)
        addActor(currentShip)
    }

    private fun createLabel(skin: Skin, name: String, width: Float, posX: Float, posY: Float, color: Color = Color.ROYAL, colorText: Color = Color.RED): Label {
        val label = Label(name, skin, "default")
        label.width = width
        label.setPosition(posX, posY)
        label.color = color
        label.style.fontColor = colorText
        return label
    }

    private fun createButton(skin: Skin, name: String, width: Float, posX: Float, posY: Float, color: Color = Color.ROYAL, colorText: Color = Color.RED, listener: InputListener): TextButton {
        val button = TextButton(name, skin, "default")
        button.width = width
        button.setPosition(posX, posY)
        button.color = color
        button.style.fontColor = colorText
        button.addListener(listener)
        return button
    }

    override fun act(delta: Float) {
        super.act(delta)
        diceResult.setText("${(space.stepsLeft())}/${(space.diceResult - 1)}")
        currentShip.setText(space.getCurrentShip().gameColor.name)
        currentPhase.setText(space.phase.name)
    }

}