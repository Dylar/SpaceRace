package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.ItemMenu
import de.bitb.spacerace.ui.EndRoundMenu

class GameGuiStage(val space: BaseSpace, val screen: GameScreen) : BaseGuiStage() {

    private var itemMenu: ItemMenu = ItemMenu(space, this)
    private var endRoundMenu: EndRoundMenu = EndRoundMenu(space, this)

    private val shipLabels: MutableList<Label> = ArrayList()
    private val creditsLabels: MutableList<Label> = ArrayList()
    private val itemsButtons: MutableList<TextButton> = ArrayList()

    private lateinit var diceLabel: Label
    private lateinit var phaseLabel: Label

    init {

        val background = Image(TextureCollection.guiBackground)
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

    private fun createInfoGroup(): Group {
        val infos: MutableList<Actor> = ArrayList()
        for (ship in space.ships) {
            var widget: Actor = createButton(listener = object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    itemMenu.toggle()
                    return true
                }
            })
            itemsButtons.add(widget as TextButton)
            infos.add(widget)

            widget = createLabel()
            creditsLabels.add(widget)
            infos.add(widget)

            widget = createLabel()
            shipLabels.add(widget)
            infos.add(widget)
        }
        return createGroup(*infos.map { it }.toTypedArray())
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
        return createGroup(diceLabel, diceBtn)
    }

    private fun createPhaseGroup(): Group {
        val phaseBtn = createButton(name = "Phase", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.nextPhase()
                if (space.isNextTurn()) {
                    endRoundMenu.toggle()
                }
                return true
            }
        })

        phaseLabel = createLabel()
        return createGroup(phaseLabel, phaseBtn)
    }

    override fun act(delta: Float) {
        super.act(delta)
        val diceResult = if (space.diced) "${(space.diceResult - space.stepsLeft())}/${(space.diceResult)}" else "0/0"
        diceLabel.setText(diceResult)
        phaseLabel.setText(space.phase.name)

        val ships = space.ships
        for (ship in ships.withIndex()) {
            shipLabels[ship.index].setText(ship.value.gameColor.name)
            creditsLabels[ship.index].setText(ship.value.credits.toString())
            itemsButtons[ship.index].setText(ship.value.items.size.toString())
        }
    }


    //TEST
    private class ItemsDialog : Dialog("Items", TextureCollection.skin, "default") {
        override fun result(result: Any?) {
            Logger.println("Result: $result")
        }
    }

    private fun openItemsDialog() {
        Logger.println("OPEN ITEMS")
        val dialog = ItemsDialog()
        dialog.text("Are you sure you want to yada yada?")
        for (item in space.currentShip.items) {
            item.toString()
            val image2 = TextureRegionDrawable(TextureRegion(TextureCollection.blackhole))
            val image1 = TextureRegionDrawable(TextureRegion(TextureCollection.blueField))
            val image3 = TextureRegionDrawable(TextureRegion(TextureCollection.yellowField))
            val btn = ImageButton(image1, image2, image3)

            dialog.button(btn, item)
        }
//        dialog.button("Yes", true) //sends "true" as the result
        dialog.button("No", false) //sends "false" as the result
        dialog.show(this)
    }

}