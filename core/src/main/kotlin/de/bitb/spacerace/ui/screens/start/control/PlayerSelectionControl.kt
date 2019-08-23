package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.events.commands.start.SelectPlayerCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.start.StartGuiStage
import org.greenrobot.eventbus.EventBus

class PlayerSelectionControl(guiStage: StartGuiStage) : BaseGuiControl(guiStage) {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        initPlayers()

        pack()
    }

    private fun initPlayers() {
        PlayerColor.values()
                .forEach { playerColor ->
                    if (playerColor != PlayerColor.NONE) {
                        val checkBox = addCheckbox(playerColor)
                        checkBox.isChecked = SELECTED_PLAYER.contains(playerColor)
                    }
                }
    }

    private fun addCheckbox(color: PlayerColor): CheckBox {
        val checkBox = createCheckbox(name = color.name,
                fontSize = GAME_SIZE_FONT_SMALL,
                fontColor = color.color,
                listener = object : InputListener() {
                    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                        EventBus.getDefault().post(SelectPlayerCommand(color))
                        return true
                    }
                })

        addCell(checkBox)
        val box = checkBox.cells.get(0)
        addPaddingRight(box)

        row()
        return checkBox
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 5)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

}