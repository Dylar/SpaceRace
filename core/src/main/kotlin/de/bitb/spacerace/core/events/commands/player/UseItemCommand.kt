package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ActivatableItem
import de.bitb.spacerace.database.items.DisposableItem
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.createGraphic
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.usecase.game.action.items.*
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class UseItemCommand(
        private var itemType: ItemType = ItemType.NONE_ITEM,
        playerData: PlayerData = NONE_PLAYER_DATA,
        private var disable: Boolean = false
) : BaseCommand(playerData) {

    companion object {
        fun get(itemType: ItemType,
                playerData: PlayerData,
                disable: Boolean = false
        ) = CommandPool.getCommand(UseItemCommand::class)
                .also {
                    it.itemType = itemType
                    it.DONT_USE_THIS_PLAYER_DATA = playerData
                    it.disable = disable
                }
    }

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var equipItemUsecase: EquipItemUsecase
    @Inject
    protected lateinit var activateUsecase: ActivateItemUsecase
    @Inject
    protected lateinit var disposeItemUsecase: DisposeItemUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        when (itemType.getDefaultInfo()) {
            is EquipItem -> equipItem()
            is ActivatableItem -> activateItem()
            is DisposableItem -> disposeItem()
        }
    }

    private fun equipItem() {
        val config = EquipItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, itemType, disable)
        compositDisposable += equipItemUsecase.getResult(
                params = config,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }

    private fun activateItem() {
        val config = ActivateItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, itemType)
        compositDisposable += activateUsecase.getResult(
                params = config,
                onSuccess = resetOnSuccess(),
                onError = resetOnError()
        )
    }

    private fun disposeItem() {
        val config = DisposeItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, itemType)
        compositDisposable += disposeItemUsecase.getResult(
                params = config,
                onSuccess = {
                    addItemsToField(it)
                    reset()
                },
                onError = resetOnError()
        )
    }

    private fun addItemsToField(result: UseItemResult) {
        val fieldGraphic = graphicController.getFieldGraphic(result.playerData.positionField.target.gamePosition)
        val itemGraphic = result.itemData.itemInfo.createGraphic(result.playerData.playerColor)
        itemGraphic.itemImage.setRotating(itemGraphic, fieldGraphic.fieldImage, fieldGraphic.fieldImage.width * 0.7)
        fieldGraphic.addItem(itemGraphic)
    }

}