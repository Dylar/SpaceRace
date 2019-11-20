package de.bitb.spacerace.ui.player.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisWindow
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRStorageMenu(val playerColor: PlayerColor)
    : VisWindow("Storage"),
        GuiBackstack by GuiBackstackHandler,
        GuiBuilder {

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        debug = DEBUG_LAYOUT
        MainGame.appComponent.inject(this)

        pad(70f, 10f, 10f, 10f)
        titleLabel.setAlignment(Align.center)
        style = WindowStyle().also {
            it.titleFont = TexturePool.bitmapFont
            it.titleFontColor = Color.TEAL
            it.background = TexturePool.getBackground(IMAGE_PATH_GUI_BACKGROUND)
        }
        setContent()
        centerWindow()
        pack()
        center()
        fadeIn()
    }

    private fun setContent() {
        val items = graphicController.getStorageItemMap(playerDataSource.getDBPlayerByColor(playerColor).first())

        items.forEach { (itemType, itemGraphic) ->
            val texture = itemGraphic.itemImage.animation.getDefaultTexture()
            val imageUp = texture ?: error("NO ANIMATION")
            val imageDown = texture
            val createBtn = createTextButtons(
                    "",
                    imageUp = TexturePool.getNinePatch(imageUp),
                    imageDown = TexturePool.getNinePatch(imageDown)
            ) { openItemDetails(itemType) }
            add(createBtn).expand()
        }

        row().pad(20f).colspan(items.size + 2)
        addButton("Cancel") {
            onBack()
        }
    }

    private fun openItemDetails(item: ItemType) {
        EventBus.getDefault().post(GuiNavi.ItemDetailMenu(playerColor, item))
    }

    private fun addButton(text: String, listener: () -> Unit) {
        createTextButtons(
                text = text,
                listener = listener)
                .also {
                    add(it).expandX()
                }
    }
}