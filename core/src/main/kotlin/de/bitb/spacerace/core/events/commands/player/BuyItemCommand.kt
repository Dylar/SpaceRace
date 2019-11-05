package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.items.shop.BuyItemConfig
import de.bitb.spacerace.usecase.game.action.items.shop.BuyItemUsecase
import javax.inject.Inject

class BuyItemCommand(
        var itemType: ItemType = ItemType.NONE_ITEM,
        buyer: PlayerColor = PlayerColor.NONE
) : BaseCommand(buyer) {

    companion object {
        fun get(itemType: ItemType,
                buyer: PlayerColor
        ) = CommandPool.getCommand(BuyItemCommand::class)
                .also {
                    it.itemType = itemType
                    it.player = buyer
                }
    }

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var buyItemUsecase: BuyItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        val buyItemConfig = BuyItemConfig(player, itemType)
        buyItemUsecase.getResult(
                params = buyItemConfig,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }

}