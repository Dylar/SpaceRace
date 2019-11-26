package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.items.shop.BuyItemConfig
import de.bitb.spacerace.usecase.game.action.items.shop.BuyItemUsecase
import de.bitb.spacerace.usecase.game.action.items.shop.SellItemConfig
import de.bitb.spacerace.usecase.game.action.items.shop.SellItemUsecase
import javax.inject.Inject

class SellItemCommand(
        private var itemType: ItemType = ItemType.NONE_ITEM,
        seller: PlayerColor = PlayerColor.NONE
) : BaseCommand(seller) {

    companion object {
        fun get(itemType: ItemType,
                buyer: PlayerColor
        ) = CommandPool.getCommand(SellItemCommand::class)
                .also {
                    it.itemType = itemType
                    it.player = buyer
                }
    }

    @Inject
    protected lateinit var sellItemUsecase: SellItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        val sellItemConfig = SellItemConfig(player, itemType)
        sellItemUsecase.getResult(
                params = sellItemConfig,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }

}