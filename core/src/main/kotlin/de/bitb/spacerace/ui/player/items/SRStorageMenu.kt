package de.bitb.spacerace.ui.player.items

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.ui.screens.GuiNavi.ItemDetailMenu
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRStorageMenu(
        private val playerColor: PlayerColor
) : SRWindowGui() {


    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    init {
        initWindow()
    }

    override fun inject() {
        MainGame.appComponent.inject(this)
    }

    override fun getTitle(): String = "Storage"

    override fun setContent() {
        val player = playerDataSource.getDBPlayerByColor(playerColor).first()
        val items = graphicController.getStorageItemMap(player)

        items.forEach { (itemType, itemGraphic) ->
            val texture = itemGraphic.itemImage.animation.getDefaultTexture()
            val imageUp = texture ?: error("NO ANIMATION")
            val createBtn = createTextButtons(
                    "",
                    imageUp = TexturePool.getNinePatch(imageUp),
                    imageDown = TexturePool.getNinePatch(texture)
            ) { openItemDetails(itemType) }
            add(createBtn).expand()
        }

        row().pad(20f).colspan(items.size + 2)
        addButton("Cancel") {
            onBack()
        }
    }

    private fun openItemDetails(item: ItemType) {
        EventBus.getDefault().post(ItemDetailMenu(playerColor, item))
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