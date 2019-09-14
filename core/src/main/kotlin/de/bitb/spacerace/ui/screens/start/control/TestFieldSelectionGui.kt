package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.events.commands.start.SelectTestFieldCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.ui.screens.start.StartGuiStage
import org.greenrobot.eventbus.EventBus


class TestFieldSelectionGui(guiStage: StartGuiStage) : BaseGuiControl(guiStage) {

    init {

        for (value in FieldType.values()) {
            if (value != FieldType.UNKNOWN) {
                val checkBox = addCheckbox(value)
                val playerSelected = DEBUG_TEST_FIELD.contains(value)
                checkBox.isChecked = playerSelected
            }
        }

        pack()

    }

    private fun addCheckbox(fieldType: FieldType): CheckBox {
        val checkBox = createCheckbox(name = fieldType.name, fontSize = GAME_SIZE_FONT_SMALL, fontColor = Color.BLACK, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(SelectTestFieldCommand(fieldType))
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