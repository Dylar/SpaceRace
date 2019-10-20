package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ActivatableItem
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.usecase.game.action.items.ActivateItemConfig
import de.bitb.spacerace.usecase.game.action.items.ActivateItemUsecase
import de.bitb.spacerace.usecase.game.action.items.EquipItemConfig
import de.bitb.spacerace.usecase.game.action.items.EquipItemUsecase
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class UseItemCommand(
        private val item: ItemInfo,
        playerData: PlayerData
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var equipItemUsecase: EquipItemUsecase
    @Inject
    protected lateinit var activateUsecase: ActivateItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        when (item) {
            is EquipItem -> equipItem()
            is ActivatableItem -> activateItem()
        }
    }

    private fun equipItem() {
        val config = EquipItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, item)
        compositDisposable += equipItemUsecase.getResult(
                params = config,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }

    private fun activateItem() {
        val config = ActivateItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, item)
        compositDisposable += activateUsecase.getResult(
                params = config,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }


}