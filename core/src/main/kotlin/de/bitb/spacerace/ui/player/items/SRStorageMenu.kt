package de.bitb.spacerace.ui.player.items

import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRGuiGrid
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

    override fun getTitle(): String = GameGuiStrings.GAME_TITLE_STORAGE

    override fun setContent() {
        val player = playerDataSource.getDBPlayerByColor(playerColor).first()
        val items = graphicController.getStorageItemMap(player)

        addItemImages(items)
        addCancelBtn(items)
    }

    private fun addItemImages(items: Map<ItemType, ItemGraphic>) {
        SRGuiGrid()
                .addItems(items = items.values.toList()) { itemGraphic ->
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
        EventBus.getDefault().post(ItemDetailMenu(playerColor, item))
    }

    private fun addCancelBtn(items: Map<ItemType, ItemGraphic>) {
        row().pad(20f).colspan(items.size + 2)
        createTextButtons(GameGuiStrings.GAME_BUTTON_CANCEL) { onBack() }
                .also { add(it).expandX() }
    }

}