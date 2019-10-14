package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.usecase.game.action.items.EquipItemConfig
import de.bitb.spacerace.usecase.game.action.items.EquipItemUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class UseItemCommand(
        private val item: ItemInfo,
        playerData: PlayerData
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var equipItemUsecase: EquipItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        val config = EquipItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, item)
        compositDisposable += equipItemUsecase.getResult(
                params = config,
                onSuccess = {
                    Logger.println("ITEM USED: $it")
                    reset()
                },
                onError = {
                    it.printStackTrace()
                    reset()
                }
        )
    }

}