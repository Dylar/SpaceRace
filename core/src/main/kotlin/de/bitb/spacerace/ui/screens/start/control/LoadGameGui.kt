package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.SaveGame
import de.bitb.spacerace.events.commands.start.LoadGameCommand
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.start.StartGuiStage
import io.objectbox.Box
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class LoadGameGui(
        guiStage: StartGuiStage
) : BaseGuiControl(guiStage) {

    @Inject
    protected lateinit var saveGameBox: Box<SaveGame>

    private val maxSpan = 7

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        MainGame.appComponent.inject(this)

        saveGameBox.all.forEach {
            addStartButton(it)
        }

        pack()
    }

    private fun addStartButton(saveGame: SaveGame) {
        val startBtn = createButton(name = saveGame.name, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(LoadGameCommand(saveGame))
                return true
            }
        })
        val cell = addCell(startBtn)
        setFont(cell.actor)
        row()
    }

    private fun <T : Actor> addCell(actor: T, colspan: Int = maxSpan): Cell<T> {
        val cell = super.add(actor)
        addPaddingTopBottom(cell, Dimensions.GameGuiDimensions.GAME_LABEL_PADDING / 4)
        addPaddingLeftRight(cell)
        cell.fill()
        cell.colspan(colspan)
        return cell
    }
}