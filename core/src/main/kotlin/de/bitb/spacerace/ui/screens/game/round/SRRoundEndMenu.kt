package de.bitb.spacerace.ui.screens.game.round

import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.core.events.commands.phases.StartNextRoundCommand
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.player.PlayerAnimation
import de.bitb.spacerace.grafik.model.player.PlayerGraphics
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus

class SRRoundEndMenu : SRWindowGui() {

    init {
        initWindow()
    }

    override fun getTitle(): String = "End round"
    override fun setContent() {
        val playerGraphics = graphicController.playerGraphics
        addPlayerImages(playerGraphics)
        addButtons(playerGraphics)
    }

    private fun addPlayerImages(playerGraphics: MutableList<PlayerGraphics>) {
        SRGuiGrid()
                .addItems(items = playerGraphics.toList()) { player ->
                    val animation = player.playerImage.animation as PlayerAnimation
                    val imageUp = animation.getFirstImage()?.texture ?: error("NO ANIMATION")
                    val imageDown = animation.getLastImage()?.texture ?: error("NO ANIMATION")
                    createTextButtons(
                            "",
                            imageUp = TexturePool.getDrawable(imageUp).tint(player.playerColor.color),
                            imageDown = TexturePool.getDrawable(imageDown).tint(player.playerColor.color)
                    ) { openPlayerDetails(player) }
                }
                .also { add(it).expand() }
    }

    private fun openPlayerDetails(player: PlayerGraphics) {
        EventBus.getDefault().post(GuiNavi.PlayerEndDetailsMenu(player.playerColor))
    }

    private fun addButtons(playerGraphics: MutableList<PlayerGraphics>) {
        row().pad(20f).colspan(playerGraphics.size + 2)
        addButton(GameGuiStrings.GAME_BUTTON_CONTINUE) {
            clearBackstack()
            EventBus.getDefault().post(StartNextRoundCommand.get())
        }
    }

    private fun addButton(text: String, listener: () -> Unit) {
        createTextButtons(
                text = text,
                listener = listener)
                .also { add(it).expandX() }
    }
}