package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.CameraActions
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.game.GameScreen

class ViewControlGui(
        val screen: GameScreen
) : Table(TextureCollection.skin),
        GuiComponent {

    private var centerBtn: TextButton

    init {
        debug = DEBUG_LAYOUT

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

        centerBtn = createButton(name = "(O)", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                screen.centerCamera()
                updateButtons()
                return true
            }
        })
        setFont(add(plusBtn).actor)
        row()
        setFont(add(centerBtn).actor)
        row()
        setFont(add(minusBtn).actor)

        pack()

        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
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

    private fun updateButtons() {
        centerBtn.setText(if (screen.cameraStatus == CameraActions.CAMERA_FREE) "(O)" else "(X)")
    }

    override fun act(delta: Float) {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) -> {
                screen.centerCamera()
                updateButtons()
            }
            Gdx.input.isKeyJustPressed(Input.Keys.SLASH) -> screen.onZoomMinusClicked()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET) -> screen.onZoomPlusClicked()
        }
        super.act(delta)
    }

}