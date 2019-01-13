package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage

abstract class BaseGuiControl(val guiStage: StartGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {
    val inputHandler: InputHandler = guiStage.inputHandler
    val gameController: GameController = guiStage.gameController

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        pack()

    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {

    }
}