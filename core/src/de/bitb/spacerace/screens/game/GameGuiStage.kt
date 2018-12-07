package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.game.EndRoundMenu
import de.bitb.spacerace.ui.game.RoundDetails
import de.bitb.spacerace.ui.player.ItemMenu
import de.bitb.spacerace.ui.player.PlayerStats

class GameGuiStage(val space: BaseSpace, val screen: GameScreen) : BaseGuiStage() {

    private var itemMenu = ItemMenu(space, this)
    private var endRoundMenu = EndRoundMenu(space, this)
    private var roundDetails = RoundDetails(space, this)

    private lateinit var itemsButton: TextButton

    private var playerStats: PlayerStats = PlayerStats(space)

    init {

//        val background = Image(TextureCollection.guiBackground)
//        background.width = guiWidth
//        background.height = guiHeight
//        background.setPosition(guiPosX, 0f)
//        addActor(background)
//
        val phaseGroup = createPhaseGroup()
        phaseGroup.x = Gdx.graphics.width - slotWidth - singlePadding
        addActor(phaseGroup)

        val diceGroup = createDiceGroup()
        diceGroup.x = phaseGroup.x - slotWidth - singlePadding
        addActor(diceGroup)
//
//        val zoomGroup = createZoomGroup()
//        zoomGroup.x = phaseGroup.x
//        zoomGroup.y = phaseGroup.height
//        addActor(zoomGroup)
//
//        val infoGroup = createInfoGroup()
//        infoGroup.y = Gdx.graphics.height - infoGroup.height
//        addActor(infoGroup)

        addActor(playerStats)
    }

    private fun createInfoGroup(): Group {
        itemsButton = createButton(name = "Items", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                itemMenu.toggle()
                return true
            }
        })
        return createGroup(itemsButton)
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
        return createGroup(diceBtn)
    }

    private fun createPhaseGroup(): Group {
        val phaseBtn = createButton(name = "Phase", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.nextPhase()
                if (space.isNextTurn()) {
                    endRoundMenu.toggle()
                }
//                playerStats.update()
                return true
            }
        })

        return createGroup(phaseBtn)
    }

    fun openRoundDetails(ship: Player) {
        roundDetails.toggle(ship)
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
        for (item in space.currentPlayer.items) {
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