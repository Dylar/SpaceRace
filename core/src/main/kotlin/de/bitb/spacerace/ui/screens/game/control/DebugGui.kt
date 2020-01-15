package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.getWorldInputCoordination
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.base.GuiComponent

class DebugGui(
        val screen: BaseScreen
) : Table(TextureCollection.skin),
        GuiComponent by object : GuiComponent {} {

    private var label: Label

    init {
        debug = DEBUG_LAYOUT

        label = createLabel(name = "00000000000")
        setFont(add(label).actor)

        pack()

        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        x = SCREEN_WIDTH / 2 - width / 2
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
        val position = getWorldInputCoordination(stage.camera)
//        x = position.posX
//        y = position.posY
        label.setText("${"%.1f".format(position.posX)}, ${"%.1f".format(position.posY)}")
        pack()
    }

}