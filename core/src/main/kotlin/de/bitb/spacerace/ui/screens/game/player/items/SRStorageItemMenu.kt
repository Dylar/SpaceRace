package de.bitb.spacerace.ui.screens.game.player.items

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.player.UseItemCommand
import de.bitb.spacerace.database.items.ActivatableItem
import de.bitb.spacerace.database.items.DisposableItem
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.createGraphic
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.grafik.model.items.getText
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.usecase.DisposableHandler
import de.bitb.spacerace.usecase.game.observe.ObservePlayerUseCase
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRStorageItemMenu(
        val playerColor: PlayerColor,
        val itemType: ItemType
) : SRWindowGui(), DisposableHandler {
    override val compositeDisposable = CompositeDisposable()

    private var span: Int = 0
    private var unusedBtn: VisTextButton? = null

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    @Inject
    protected lateinit var observePlayerUseCase: ObservePlayerUseCase

    protected lateinit var player: PlayerData
    protected val itemInfo = itemType.getDefaultInfo()

    init {
        initWindow()
        initObserver()
    }

    private fun initObserver() {
        observePlayerUseCase.observeStream(playerColor) { player ->
            this.player = player
            titleLabel.setText(getTitle())
            unusedBtn?.isDisabled = player.equippedItems.count { it.itemInfo.type == itemType } == 0
        }.addDisposable()
    }

    override fun inject() {
        MainGame.appComponent.inject(this)
        player = playerDataSource.getDBPlayerByColor(playerColor).first()
    }

    override fun getTitle(): String {
        val storageItems = player.storageItems.count { it.itemInfo.type == itemType }
        val equippedItems = when (itemInfo) {
            is EquipItem -> player.equippedItems.count { it.itemInfo.type == itemType }.let { " / $it )" }
            else -> " )"
        }

        return "${itemType.name} ( $storageItems$equippedItems"
    }

    override fun setContent() {
        span = if (itemInfo is EquipItem) 6 else 4
        addItemImage(span)
        addItemText(span)
        addButtons(span)
    }

    private fun addItemImage(span: Int) {
        val item = itemType.createGraphic(playerColor).getDisplayImage()
        add(item).width(GAME_BUTTON_WIDTH_DEFAULT)
                .height(GAME_BUTTON_WIDTH_DEFAULT)
                .center()
                .colspan(span)
    }

    private fun addItemText(span: Int) {
        row().pad(20f).colspan(span)

        createLabel(text = itemType.getText())
                .also {
                    it.setAlignment(Align.center)
                    add(it).colspan(span)
                }
    }

    private fun addButtons(span: Int) {
        row().pad(20f).colspan(span)
        if (itemInfo is EquipItem) {
            addButton(setUnuseButton()) { unequipItem() }.also { unusedBtn = it }
        }
        addButton(getUseButtonText()) { useItem() }
        addButton(GameGuiStrings.GAME_BUTTON_CANCEL) { onBack() }
    }

    private fun unequipItem() {
        EventBus.getDefault().post(UseItemCommand.get(itemType, player.playerColor, true))
    }

    private fun useItem() {
        EventBus.getDefault().post(UseItemCommand.get(itemType, player.playerColor))
    }

    private fun addButton(text: String, span: Int = 2, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener)
                    .also { add(it).colspan(span) }

    private fun getUseButtonText() =
            when (itemType.getDefaultInfo()) {
                is EquipItem -> "EQUIP"
                is DisposableItem -> "DISPOSE"
                is ActivatableItem -> "USE"
                else -> "-"

            }

    private fun setUnuseButton() =
            when (itemType.getDefaultInfo()) {
                is EquipItem -> "UNEQUIP"
                else -> "-"
            }

}