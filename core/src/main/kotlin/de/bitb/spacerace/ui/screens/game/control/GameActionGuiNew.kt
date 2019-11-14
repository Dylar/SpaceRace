package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.dimensions.Dimensions.BUTTON_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.BUTTON_WIDTH
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.core.events.commands.player.DiceCommand
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.TextureCollection.bitmapFont
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class GameActionGuiNew : VisTable() {

    @Inject
    protected lateinit var playerController: PlayerController

    val padding = 10f

    init {
        MainGame.appComponent.inject(this)

        setBackground()
        setContent()
        setPosition()
    }

    private fun getNinePatch(imagePath: String): NinePatchDrawable {
        val texture = Texture(Gdx.files.internal(imagePath))
        val width = texture.width - 2
        val height = texture.height - 2

        val left = width / 3
        val right = width / 3
        val bot = height / 3
        val top = height / 3

        return NinePatchDrawable(NinePatch(TextureRegion(texture, 1, 1, width, height), left, right, top, bot))
    }

    private fun setContent() {
        val storageBtn = createButtons(text = "Storage",
                imageUp = TextureCollection.buttonUp,
                imageDown = TextureCollection.buttonDown,
                listener = {
                    val storageMenu : GuiNavi = GuiNavi.StorageMenu(playerController.currentColor)
                    EventBus.getDefault().post(storageMenu)
                })
        val diceBtn = createButtons(text = "Dice",
                imageUp = TextureCollection.buttonUp,
                imageDown = TextureCollection.buttonDown,
                listener = { EventBus.getDefault().post(DiceCommand.get(playerController.currentColor)) })
        val continueBtn = createButtons(text = "Continue",
                imageUp = TextureCollection.buttonUp,
                imageDown = TextureCollection.buttonDown,
                listener = { EventBus.getDefault().post(NextPhaseCommand.get(playerController.currentColor)) })

        diceBtn.x = 0f
        diceBtn.y = 0f
        continueBtn.x = BUTTON_WIDTH + padding
        continueBtn.y = 0f

        storageBtn.x = BUTTON_WIDTH + padding
        storageBtn.y = BUTTON_HEIGHT + padding

        addActor(storageBtn)
        addActor(diceBtn)
        addActor(continueBtn)
    }

    private fun setBackground() {
//        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
    }

    private fun createButtons(
            text: String,
            imageUp: String,
            imageDown: String,
            listener: () -> Unit
    ): VisTextButton {

        val style = VisTextButton.VisTextButtonStyle()
        style.up = getNinePatch(imageUp)
        style.down = getNinePatch(imageDown)
        style.font = bitmapFont
        style.downFontColor = Color.RED
        style.fontColor = Color.TEAL
        style.disabledFontColor = Color.DARK_GRAY

        val diceBtn = VisTextButton(text, style)
        diceBtn.addListener(createListener(listener))
        diceBtn.width = BUTTON_WIDTH
        diceBtn.height = BUTTON_HEIGHT
//        diceBtn.background = getNinePatch(TextureCollection.buttonUp)
        return diceBtn
    }

    private fun createListener(action: () -> Unit) =
            object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    action()
                }
            }

    private fun setPosition() {
        width = BUTTON_WIDTH * 2
        height = BUTTON_HEIGHT * 2
        x = (SCREEN_WIDTH - (BUTTON_WIDTH * 2 + padding * 2))
        y = padding * 2
    }

    override fun clear() {
        super.clear()
    }
}