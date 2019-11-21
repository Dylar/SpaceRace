package de.bitb.spacerace.ui.player.items

import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.ui.screens.GuiNavi
import org.greenrobot.eventbus.EventBus

class SRStorageItemMenu(
        val playerColor: PlayerColor,
        val itemType: ItemType
) : SRWindowGui() {

    init {
        initWindow()
    }

    override fun getTitle(): String = itemType.name

    override fun setContent() {
        val player = playerDataSource.getDBPlayerByColor(playerColor).first()
        val items = graphicController.getStorageItemMap(player)

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