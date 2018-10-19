package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace

class GameGuiStage(val space: BaseSpace, val screen: GameScreen) : BaseStage() {

    private lateinit var shipLabel: Label
    private lateinit var creditsLabel: Label

    private lateinit var diceLabel: Label
    private lateinit var phaseLabel: Label

    private var skin: Skin = Skin(Gdx.files.internal("uiskin.json"))
    private val slotHeight = 32f
    private val slotWidth = 120f
    private val singlePadding = slotWidth * 0.2f
    private val guiHeight = slotHeight * 2
    private val guiWidth = slotWidth * 2 + singlePadding * 2
    private val guiPosX = Gdx.graphics.width - guiWidth

    init {

        var background = Image(TextureCollection.guiBackground)
        background.width = guiWidth
        background.height = guiHeight
        background.setPosition(guiPosX, 0f)
        addActor(background)

        val phaseGroup = createPhaseGroup()
        phaseGroup.x = Gdx.graphics.width - slotWidth - singlePadding
        addActor(phaseGroup)

        val diceGroup = createDiceGroup()
        diceGroup.x = phaseGroup.x - slotWidth - singlePadding
        addActor(diceGroup)

        val zoomGroup = createZoomGroup()
        zoomGroup.x = phaseGroup.x
        zoomGroup.y = phaseGroup.height
        addActor(zoomGroup)

        val infoGroup = createInfoGroup()
        infoGroup.y = Gdx.graphics.height - infoGroup.height
        addActor(infoGroup)

    }

    private fun createGroup(vararg actors: Actor): Group {
        val group = Group()

        group.width = slotWidth + singlePadding * 2
        group.height = slotHeight * actors.size

        val background = Image(TextureCollection.guiBackground)
        background.width = group.width
        background.height = group.height
        group.addActor(background)
        var posY = 0f
        for (actor in actors) {
            actor.setPosition(singlePadding, posY)
            group.addActor(actor)
            posY += slotHeight
        }

        return group
    }

    private fun createInfoGroup(): Group {
        creditsLabel = createLabel()

        posY += slotHeight
        shipLabel = createLabel()

        return createGroup(shipLabel)
    }

    private fun createZoomGroup(): Group {
        val zoomPlusBtn = createButton(name = "+", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.currentZoom--
                screen.zoom()
                return true
            }
        })

        val zoomMinusBtn = createButton(listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.currentZoom++
                screen.zoom()
                return true
            }
        })

        val followBtn = createButton(name = "Lock", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.lockCamera()
                return true
            }
        })

        return createGroup(zoomMinusBtn, zoomPlusBtn, followBtn)
    }

    private fun createDiceGroup(): Group {
        val diceBtn = createButton(name = "Dice", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.dice()
                return true
            }
        })
        diceLabel = createLabel()
        return createGroup(diceBtn, diceLabel)
    }

    private fun createPhaseGroup(): Group {
        val phaseBtn = createButton(name = "Phase", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.nextPhase()
                return true
            }
        })

        phaseLabel = createLabel()
        return createGroup(phaseBtn, phaseLabel)
    }

    private fun createLabel(skin: Skin = this.skin, name: String = "-", width: Float = slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED): Label {
        val label = Label(name, skin, "default")
        label.width = width
        label.setPosition(posX, posY)
        label.color = color
        label.style.fontColor = colorText
        return label
    }

    private fun createButton(skin: Skin = this.skin, name: String = "-", width: Float = slotWidth, posX: Float = 0f, posY: Float = 0f, color: Color = Color.ROYAL, colorText: Color = Color.RED, listener: InputListener): TextButton {
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
        val diceResult = if (space.diced) "${(space.diceResult - space.stepsLeft())}/${(space.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)
        phaseLabel.setText(space.phase.name)

        val ship = space.getCurrentShip()
        shipLabel.setText(ship.gameColor.name)
        creditsLabel.setText(ship.credits.toString())

    }

}