package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.events.commands.start.ChangeTestFieldCommand
import de.bitb.spacerace.events.commands.start.SelectMapCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage


class MapSelectionControl(val gameController: GameController, val guiStage: StartGuiStage, val inputHandler: InputHandler = guiStage.inputHandler) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val buttonGroup: ButtonGroup<CheckBox> = ButtonGroup()
        buttonGroup.setMaxCheckCount(1)
        buttonGroup.setMinCheckCount(1)
        buttonGroup.setUncheckLast(true)

        for (value in MapCollection.values()) {
            val checkBox = addCheckbox(value)
            buttonGroup.add(checkBox)
            if (value == MapCollection.TEST_MAP) {
                checkBox.isChecked = true
                inputHandler.handleCommand(SelectMapCommand(value))
            }
        }

        addTestField()

        pack()

        setPosition()
    }

    private lateinit var testFieldBtn: TextButton

    private fun addTestField() {
        testFieldBtn = createButton(name = FieldType.RANDOM.name, fontSize = GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(ChangeTestFieldCommand())
                return true
            }
        })

        addCell(testFieldBtn)
        row()
    }

    private fun addCheckbox(mapCollection: MapCollection): CheckBox {
        val checkBox = createCheckbox(name = mapCollection.name, fontSize = GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(SelectMapCommand(mapCollection))
                return true
            }
        })

        addCell(checkBox)
        val box = checkBox.cells.get(0)
        addPaddingRight(box)

        row()
        return checkBox
    }

    private fun setPosition() {
        x = SCREEN_WIDTH - width - width / 4
        y = SCREEN_HEIGHT / 2f - height / 2
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 5)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        testFieldBtn.label.setText(DEBUG_TEST_FIELD.name)
    }

}