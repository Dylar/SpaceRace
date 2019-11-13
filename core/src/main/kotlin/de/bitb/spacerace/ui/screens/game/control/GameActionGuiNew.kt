package de.bitb.spacerace.ui.screens.game.control

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
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
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import javax.inject.Inject

class GameActionGuiNew(
        val guiStage: GameGuiStage
) : VisTable() {

    @Inject
    protected lateinit var playerController: PlayerController

    val padding = 10f
    val bitmapFont = BitmapFont(Gdx.files.internal("spaceranger.fnt"))

    init {
        MainGame.appComponent.inject(this)

        bitmapFont.data.setScale(1.0f)
        setBackground()
        setContent()

//        VisWindow()

//        val diceBtn = createButton(name = GAME_BUTTON_DICE, listener = object : InputListener() {
//            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//                EventBus.getDefault().post(DiceCommand.get(playerController.currentColor))
//                return true
//            }
//        })
//
//        val continueBtn = createButton(name = GAME_BUTTON_CONTINUE, listener = object : InputListener() {
//            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//                EventBus.getDefault().post(NextPhaseCommand.get(playerController.currentColor))
//                return true
//            }
//        })
//
//        val storageBtn = createButton(name = GAME_BUTTON_STORAGE, listener = object : InputListener() {
//            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//                openItemMenu()
//                return true
//            }
//        })
//
//        setFont(addCell(storageBtn).actor)
//        row()
//        setFont(addCell(continueBtn).actor)
//        row()
//        setFont(addCell(diceBtn).actor)
//        debug = true
//        background = getNinePatch(TextureCollection.guiBackground)
//        pack()
        setPosition()
    }


    private fun getNinePatch(image: String, width: Float, height: Float): NinePatchDrawable {
        // create a new texture region, otherwise black pixels will show up too, we are simply cropping the image
        // last 4 numbers respresent the length of how much each corner can draw,
        // for example if your image is 50px and you set the numbers 50, your whole image will be drawn in each corner
        // so what number should be good?, well a little less than half would be nice
//        val pixmap200 = Pixmap(Gdx.files.internal(image))
//        val pixmap100 = Pixmap(width.toInt(), height.toInt(), pixmap200.format)
//        pixmap100.drawPixmap(pixmap200,
//                0, 0, pixmap200.width, pixmap200.height,
//                0, 0, pixmap100.width, pixmap100.height
//        )
//        pixmap200.dispose()
//        pixmap100.dispose()
//        val texture = Texture(pixmap100)
        val texture = Texture(Gdx.files.internal(image))
        val left = texture.width / 3
        val right = texture.width / 3
        val bot = texture.height / 3
        val top = texture.height / 3

        return NinePatchDrawable(NinePatch(
                TextureRegion(texture, 1, 1, texture.width - 2, texture.height - 2),
                left,
                right,
                top,
                bot
        ))
    }

    private fun setContent() {
        val storageBtn = createButtons(text = "Storage",
                imageUp = TextureCollection.buttonBackground,
                imageDown = TextureCollection.buttonBackground1,
                listener = { Logger.justPrint("1. Button: Storage") })
        val diceBtn = createButtons(text = "Dice",
                imageUp = TextureCollection.buttonBackground,
                imageDown = TextureCollection.buttonBackground1,
                listener = { Logger.justPrint("2. Button: Dice") })
        val continueBtn = createButtons(text = "Continue",
                imageUp = TextureCollection.buttonBackground,
                imageDown = TextureCollection.buttonBackground1,
                listener = { Logger.justPrint("3. Button: Continue") })

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
//        val frame = VisTable()
//        frame.background = TextureRegionDrawable(TextureRegion(TextureCollection.buttonBackground))
//        frame.center().pack()

        val style = VisTextButton.VisTextButtonStyle()
        style.up = getNinePatch(imageUp, BUTTON_WIDTH, BUTTON_HEIGHT)
        style.down = getNinePatch(imageDown, BUTTON_WIDTH, BUTTON_HEIGHT)
        style.font = bitmapFont
        style.downFontColor = Color.RED
        style.fontColor = Color.TEAL
        style.disabledFontColor = Color.DARK_GRAY

//        style.imageOver = TextureRegionDrawable(TextureCollection.alienClaw)
//        style.imageChecked = TextureRegionDrawable(imageUp)
//        style.imageCheckedOver = TextureRegionDrawable(imageUp)
//        style.imageDisabled = TextureRegionDrawable(imageUp)
        val diceBtn = VisTextButton(text, style)
//        val diceBtn = VisImage(getNinePatch(imageUp, BUTTON_WIDTH, BUTTON_HEIGHT))
        diceBtn.addListener(createListener(listener))
        diceBtn.width = (BUTTON_WIDTH).toFloat()
        diceBtn.height = (BUTTON_HEIGHT).toFloat()
//        diceBtn.pack()

//        diceBtn.image.width = BUTTON_WIDTH
//        diceBtn.image.height = BUTTON_HEIGHT

//        diceBtn.image.setScaling(Scaling.stretch)
//        diceBtn.label.width = BUTTON_WIDTH
//        diceBtn.label.height = BUTTON_HEIGHT
//        diceBtn.image.x = 0f
//        diceBtn.image.y = 0f
//        diceBtn.label.x = 0f
//        diceBtn.label.y = 0f
        diceBtn.background = getNinePatch(TextureCollection.buttonBackground, BUTTON_WIDTH, BUTTON_HEIGHT)
//        diceBtn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT)
//        diceBtn.invalidate()
//        diceBtn.debug = true
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
        x = (SCREEN_WIDTH - (BUTTON_WIDTH * 2 + padding*2))
        y = padding * 2
    }

    override fun clear() {
        super.clear()
    }
}