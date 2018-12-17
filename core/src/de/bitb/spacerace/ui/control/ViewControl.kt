package de.bitb.spacerace.ui.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CENTER
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.screens.game.GameScreen
import de.bitb.spacerace.ui.base.GuiComponent

class ViewControl(val space: BaseSpace, val screen: GameScreen, guiComponent: GuiComponent = object : GuiComponent {}) : Table(TextureCollection.skin), GuiComponent by guiComponent {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val plusBtn = createButton(name = "+", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.onZoomPlusClicked()
                return true
            }
        })
        val minusBtn = createButton(name = "-", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.onZoomMinusClicked()
                return true
            }
        })

        val centerBtn = createButton(name = GAME_BUTTON_CENTER, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.centerCamera()
                return true
            }
        })

        setFont(add(plusBtn).actor)
        row()
        setFont(add(centerBtn).actor)
        row()
        setFont(add(minusBtn).actor)

        pack()

    }

    override fun <T : Actor> add(actor: T): Cell<T> {
        return addCell(super.add(actor))
    }

    private fun <T : Actor> addCell(cell: Cell<T>): Cell<T> {
        addPaddingTopBottom(cell, singlePadding / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

}