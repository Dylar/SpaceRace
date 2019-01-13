package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_LABEL_PADDING
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_LANGUAGE
import de.bitb.spacerace.config.strings.Strings.StartGuiStrings.START_BUTTON_START
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.ChangeLanguageCommand
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.events.commands.start.ChangeWinAmountCommand
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage

abstract class BaseGuiControl(val guiStage: StartGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage, InputObserver {
    val inputHandler: InputHandler = guiStage.inputHandler
    val gameController: GameController = guiStage.gameController

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        pack()

    }

}