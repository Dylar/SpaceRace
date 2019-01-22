package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.CameraActions
import de.bitb.spacerace.config.CAMERA_TARGET
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.game.GameScreen

class DebugControl(val game: MainGame) : Table(TextureCollection.skin), GuiComponent by object : GuiComponent {} {

    val screen = (game.screen as GameScreen)

    private var label: Label

    init {
        debug = DEBUG_LAYOUT

        label = createLabel(name = "00000000000")
        setFont(add(label).actor)

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

    override fun act(delta: Float) {
        super.act(delta)
        label.setText("${screen.gameStage.camera.position.x}, ${screen.gameStage.camera.position.y}")
    }

}