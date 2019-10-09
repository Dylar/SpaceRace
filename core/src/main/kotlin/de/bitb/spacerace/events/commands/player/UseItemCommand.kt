package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.usecase.game.action.UseItemConfig
import de.bitb.spacerace.usecase.game.action.UseItemUsecase
import de.bitb.spacerace.utils.Logger
import javax.inject.Inject

class UseItemCommand(
        private val item: ItemInfo,
        playerData: PlayerData
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var useItemUsecase: UseItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        val config = UseItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, item)
        useItemUsecase.getResult(
                params = config,
                onSuccess = { Logger.println("ITEM USED: $it") },
                onError = { reset() }
        )
    }

}