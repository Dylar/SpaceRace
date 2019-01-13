package de.bitb.spacerace.ui.game

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING_SPACE
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENU_END_ROUND_DETAILS_TITLE
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_CREDITS
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_MINES
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_ROUND_DETAILS_VICTORIES
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.BaseMenu

class RoundEndDetails(guiStage: GameGuiStage, endMenu: RoundEndMenu, player: Player) : BaseMenu(guiStage, endMenu) {

    init {
        addTitle(player)
        addImage(player)
        addText(player)
        addButtons()
        pack()
        setPosition()
        endMenu.closeMenu()
    }

    private fun addTitle(player: Player) {
        val cell = add(GAME_MENU_END_ROUND_DETAILS_TITLE + player.playerData.playerColor.name)
        setFont(cell.actor)
    }

    private fun addImage(player: Player) {
        row()
        val cell = add(player.getDisplayImage(player.getGameImage().texture, color = player.playerData.playerColor.color))
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText(player: Player) {
        addText(GAME_ROUND_DETAILS_VICTORIES + guiStage.gameController.victories[player.playerData.playerColor]!!)
        addText(GAME_ROUND_DETAILS_CREDITS + player.playerData.credits)

        var mineAmount = 0
        for (spaceField in guiStage.gameController.fieldController.fieldsMap[FieldType.MINE]!!) {
            if ((spaceField as MineField).owner == player.playerData.playerColor) {
                mineAmount++
            }
        }
        addText(GAME_ROUND_DETAILS_MINES + mineAmount)

    }

    private fun addText(text: String) {
        row()
        val cell = add(text)
        addPaddingTopBottom(cell, GAME_MENU_PADDING_SPACE)
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