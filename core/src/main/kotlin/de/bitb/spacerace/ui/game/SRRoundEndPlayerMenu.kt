package de.bitb.spacerace.ui.game

import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_WINDOW_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_WINDOW_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.grafik.model.player.PlayerGraphics
import de.bitb.spacerace.ui.base.SRWindowGui
import javax.inject.Inject

class SRRoundEndPlayerMenu(
        val playerColor: PlayerColor
) : SRWindowGui() {

    private val span: Int = 4

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    protected lateinit var player: PlayerData
    protected lateinit var playerGraphics: PlayerGraphics

    init {
        initWindow()
    }

    override fun inject() {
        MainGame.appComponent.inject(this)
        player = playerDataSource.getDBPlayerByColor(playerColor).first()
        playerGraphics = graphicController.getPlayerGraphic(playerColor)
    }

    override fun getTitle(): String = "${GameGuiStrings.GAME_MENU_END_ROUND_DETAILS_TITLE}${playerColor.name}"

    override fun setContent() {
        addPlayerImage()
        addPlayerStats()
        addButtons()
    }

    private fun addPlayerImage() {
        val item = playerGraphics.getDisplayImage(color = playerColor.color)
        add(item).width(GAME_BUTTON_WIDTH_DEFAULT)
                .height(GAME_BUTTON_WIDTH_DEFAULT)
                .center()
                .colspan(span)
    }

    private fun addPlayerStats() {
        val pad = 10f
        row().pad(pad * 2, pad, pad, pad).colspan(span)
        addLabel(text = "${GameGuiStrings.GAME_ROUND_DETAILS_VICTORIES}${player.victories}")
        row().pad(pad).colspan(span)
        addLabel(text = "${GameGuiStrings.GAME_ROUND_DETAILS_MINES}${player.mines.size}")
        row().pad(pad).colspan(span)
        addLabel(text = "${GameGuiStrings.GAME_ROUND_DETAILS_CREDITS}${player.credits}")
    }

    private fun addButtons() {
        row().pad(20f).colspan(span)
        createTextButtons(GAME_BUTTON_CANCEL) { onBack() }
                .also { add(it).expandX() }
    }

    private fun addLabel(text: String) {
        createLabel(
                text = text)
                .also {
                    it.setAlignment(Align.center)
                    add(it).colspan(span)
                }
    }

}