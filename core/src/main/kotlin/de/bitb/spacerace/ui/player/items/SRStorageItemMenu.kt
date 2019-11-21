package de.bitb.spacerace.ui.player.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.player.UseItemCommand
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.createGraphic
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.grafik.model.items.getText
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRWindowGui
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRStorageItemMenu(
        val playerColor: PlayerColor,
        val itemType: ItemType
) : SRWindowGui() {

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    protected lateinit var player: PlayerData
    protected lateinit var items: List<ItemData>

    init {
        debug = true
        initWindow()
    }

    override fun inject() {
        MainGame.appComponent.inject(this)
        player = playerDataSource.getDBPlayerByColor(playerColor).first()
        items = player.storageItems.filter { it.itemInfo.type == itemType }
    }

    override fun getTitle(): String = itemType.name + " (${items.size})"

    override fun setContent() {
        val enableUnusedBtn = itemType.getDefaultInfo() is EquipItem
        val span = if (enableUnusedBtn) 6 else 4
        val item = itemType.createGraphic(playerColor).getDisplayImage()
        add(item).width(GAME_BUTTON_WIDTH_DEFAULT)
                .height(GAME_BUTTON_WIDTH_DEFAULT)
                .center()
                .colspan(span)

        row().pad(20f).colspan(span)

        createLabel(text = itemType.getText(), fontColor = Color.TEAL)
                .also {
                    it.setAlignment(Align.center)
                    add(it).colspan(span)
                }

        row().pad(20f).colspan(span)
        when {
            itemType.getDefaultInfo() is EquipItem -> addButton("Unequip") { unequipItem() }
        }
        addButton("Use") { useItem() }
        addButton("Cancel") { onBack() }
    }

    private fun unequipItem() {
        EventBus.getDefault().post(UseItemCommand.get(itemType, player.playerColor, true))
    }

    private fun useItem() {
        EventBus.getDefault().post(UseItemCommand.get(itemType, player.playerColor))
    }

    private fun addButton(text: String, span: Int = 2, listener: () -> Unit) {
        createTextButtons(
                text = text,
                listener = listener)
                .also {
                    add(it).colspan(span)
                }
    }
}