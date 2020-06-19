package de.bitb.spacerace.ui.screens.game.player.shop

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.player.BuyItemCommand
import de.bitb.spacerace.core.events.commands.player.SellItemCommand
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.createGraphic
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.grafik.model.items.getText
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.usecase.game.observe.ObservePlayerUseCase
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SRShopItemMenu(
        val playerColor: PlayerColor,
        val itemType: ItemType
) : SRWindowGui() {

    private val span = 6

    private lateinit var sellBtn: VisTextButton
    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    @Inject
    protected lateinit var observePlayerUseCase: ObservePlayerUseCase

    private lateinit var disposable: Disposable

    protected lateinit var player: PlayerData
    protected val itemInfo = itemType.getDefaultInfo()

    init {
        initWindow()
        initObserver()
    }

    private fun initObserver() {
        disposable = observePlayerUseCase.observeStream(playerColor) { player ->
            this.player = player
            titleLabel.setText(getTitle())
            sellBtn.isDisabled = player.sellableItems(itemType) == 0
        }
    }

    override fun inject() {
        MainGame.appComponent.inject(this)
        player = playerDataSource.getDBPlayerByColor(playerColor).first()
    }

    override fun getTitle(): String {
        val itemCount = player.sellableItems(itemType)
        return "${itemType.getDefaultInfo().price} ($itemCount)"
    }

    override fun setContent() {
        addItemImage()
        addItemText()
        addButtons()
    }

    private fun addItemImage() {
        val item = itemType.createGraphic(playerColor).getDisplayImage()
        add(item).width(GAME_BUTTON_WIDTH_DEFAULT)
                .height(GAME_BUTTON_WIDTH_DEFAULT)
                .center()
                .colspan(span)
    }

    private fun addItemText() {
        row().pad(20f).colspan(span)

        createLabel(text = itemType.getText())
                .also {
                    it.setAlignment(Align.center)
                    add(it).colspan(span)
                }
    }

    private fun addButtons() {
        row().pad(20f).colspan(span)
        addButton(GameGuiStrings.GAME_BUTTON_SELL) { sellItem() }.also { sellBtn = it }
        addButton(GameGuiStrings.GAME_BUTTON_BUY) { buyItem() }
        addButton(GameGuiStrings.GAME_BUTTON_CANCEL) { onBack() }
    }

    private fun buyItem() {
        EventBus.getDefault().post(BuyItemCommand.get(itemType, playerColor))
    }

    private fun sellItem() {
        EventBus.getDefault().post(SellItemCommand.get(itemType, playerColor))
    }

    private fun addButton(text: String, span: Int = 2, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener)
                    .also {
                        add(it).colspan(span)
                    }
}