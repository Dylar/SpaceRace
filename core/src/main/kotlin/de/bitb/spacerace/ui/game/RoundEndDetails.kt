package de.bitb.spacerace.ui.game

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_GUI_PADDING_SPACE
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENU_END_ROUND_DETAILS_TITLE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_MINES
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_VICTORIES
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.game.GameGuiStage

class RoundEndDetails(
        guiStage: GameGuiStage,
        player: PlayerData
) : BaseMenu(guiStage, null, player),
        GuiBackstack by GuiBackstackHandler {

    init {
        addTitle()
        addImage()
        addText()
        addButtons()
        pack()
        setPosition()
    }

    override fun refreshMenu() {
        //nothing to do
    }

    private fun addTitle() {
        val cell = add(GAME_MENU_END_ROUND_DETAILS_TITLE + player!!.playerColor.name)
        setFont(cell.actor)
    }

    private fun addImage() {
        row()
        val player = graphicController.getPlayerGraphic(player!!.playerColor)
        val cell = add(player.getDisplayImage(color = player.playerColor.color))
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText() {
        addText("$GAME_ROUND_DETAILS_VICTORIES${player?.victories}")
        addText("$GAME_ROUND_DETAILS_CREDITS${player?.credits}")
        addText("$GAME_ROUND_DETAILS_MINES${player?.mines?.size}")
    }

    private fun addText(text: String) {
        row()
        val cell = add(text)
        addPaddingTopBottom(cell, GAME_GUI_PADDING_SPACE)
        setFont(cell.actor, GAME_SIZE_FONT_SMALL)
    }

    private fun setPosition() {
        x = (Dimensions.SCREEN_WIDTH - (Dimensions.SCREEN_WIDTH / 2) - width / 2)
        y = (Dimensions.SCREEN_HEIGHT - (Dimensions.SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addButtons() {
        row()

        val cancelBtn = createButton(name = GAME_BUTTON_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onBack()
                return true
            }
        })
        val cellBtn = add(cancelBtn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)
    }
}