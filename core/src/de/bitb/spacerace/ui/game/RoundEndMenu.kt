package de.bitb.spacerace.ui.game

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_END_ROUND_WIDTH_MIN
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENU_END_ROUND_TITLE
import de.bitb.spacerace.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.base.BaseMenu

class RoundEndMenu(guiStage: GameGuiStage) : BaseMenu(guiStage) {

    init {
        val players = guiStage.gameController.playerController.players
        var size = players.size
        size = if (size < GAME_MENU_END_ROUND_WIDTH_MIN) GAME_MENU_END_ROUND_WIDTH_MIN else size

        addTitle(size)
        addPlayer(players)
        addButtons(size)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = (SCREEN_WIDTH - (SCREEN_WIDTH / 2) - width / 2)
        y = (SCREEN_HEIGHT - (SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addTitle(size: Int) {
        val cell = add(GAME_MENU_END_ROUND_TITLE)
        setFont(cell.actor)
        cell.colspan(size)
    }

    private fun addPlayer(players: MutableList<Player>) {
        row()
        for (player in players) {
            val displayImage = player.getDisplayImage()
            displayImage.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    val playerDetails = RoundEndDetails(guiStage, this@RoundEndMenu, player)
                    playerDetails.openMenu()
                    guiStage.addActor(playerDetails)
                    return true
                }
            })
            add(displayImage)
        }
    }

    private fun addButtons(size: Int) {
        row()
        val continueBtn = createButton(name = Strings.GameGuiStrings.GAME_BUTTON_CONTINUE, listener = object : InputListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                closeMenu()
                guiStage.inputHandler.handleCommand(NextPhaseCommand(thisPlayer))
                return true
            }
        })
        val cellBtn = add(continueBtn)
        cellBtn.colspan(size)
        setFont(cellBtn.actor)
    }

}