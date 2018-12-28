package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CENTER
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.game.GameScreen

class ViewControl(val game: MainGame) : Table(TextureCollection.skin), GuiComponent by object : GuiComponent {} {

    val screen = (game.screen as GameScreen)

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
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun act(delta: Float) {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) -> screen.centerCamera()
            Gdx.input.isKeyJustPressed(Input.Keys.SLASH) -> screen.onZoomMinusClicked()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET) -> screen.onZoomPlusClicked()
        }
        super.act(delta)
    }

}