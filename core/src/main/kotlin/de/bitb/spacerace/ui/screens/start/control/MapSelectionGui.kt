package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.core.events.commands.start.SelectMapCommand
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.start.StartGuiStage
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class MapSelectionGui(
        guiStage: StartGuiStage
) : BaseGuiControl(guiStage) {

    @Inject
    protected lateinit var mapDataSource: MapDataSource

    init {
        MainGame.appComponent.inject(this)
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val buttonGroup: ButtonGroup<CheckBox> = ButtonGroup()
        buttonGroup.setMaxCheckCount(1)
        buttonGroup.setMinCheckCount(1)
        buttonGroup.setUncheckLast(true)

        for (value in mapDataSource.getDBAllMaps()) {
            val checkBox = addCheckbox(value.name)
            buttonGroup.add(checkBox)
            if (value.name == SELECTED_MAP) {
                checkBox.isChecked = true
                EventBus.getDefault().post(SelectMapCommand.get(value.name))
            }
        }

        pack()

        setPosition()
    }

    private fun addCheckbox(name: String): CheckBox {
        val checkBox = createCheckbox(name = name, fontSize = GAME_SIZE_FONT_SMALL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(SelectMapCommand.get(name))
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
        x = SCREEN_WIDTH - width
        y = SCREEN_HEIGHT / 2f - height / 2
    }

    private fun <T : Actor> addCell(actor: T): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, GAME_LABEL_PADDING / 5)
        addPaddingLeftRight(cell)
        cell.fill()
        return cell
    }

}