package de.bitb.spacerace.ui.screens.game.player.shop

import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.ui.screens.NaviRoute.ShopDetailMenu
import org.greenrobot.eventbus.EventBus

class SRShopMenu(
        private val playerColor: PlayerColor
) : SRWindowGui() {

    init {
        initWindow()
    }

    override fun getTitle(): String = GameGuiStrings.GAME_TITLE_SHOP

    override fun setContent() {
        val items =  ItemInfo.getAllItems()

        addItemImages(items)
        addCancelBtn(items)
    }

    private fun addItemImages(items: MutableList<ItemGraphic>) {
        SRGuiGrid()
                .addItems(items = items) { itemGraphic ->
                    val texture = itemGraphic.itemImage.animation.getDefaultTexture()
                            ?: error("NO ANIMATION")
                    createTextButtons(
                            "",
                            imageUp = TexturePool.getDrawable(texture),
                            imageDown = TexturePool.getDrawable(texture)
                    ) { openItemDetails(itemGraphic.itemType) }
                }
                .also { add(it).expand() }
    }

    private fun openItemDetails(item: ItemType) {
        EventBus.getDefault().post(ShopDetailMenu(playerColor, item))
    }

    private fun addCancelBtn(items: MutableList<ItemGraphic>) {
        row().pad(20f).colspan(items.size + 2)
        createTextButtons(GameGuiStrings.GAME_BUTTON_CANCEL) { onBack() }
                .also { add(it).expandX() }
    }

}