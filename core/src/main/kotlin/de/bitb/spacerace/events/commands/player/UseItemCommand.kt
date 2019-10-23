package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ActivatableItem
import de.bitb.spacerace.database.items.DisposableItem
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.items.disposable.DisposableItemGraphic
import de.bitb.spacerace.usecase.game.action.items.*
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class UseItemCommand(
        private val item: ItemInfo,
        playerData: PlayerData
) : BaseCommand(playerData) {

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
        when (item) {
            is EquipItem -> equipItem()
            is ActivatableItem -> activateItem()
            is DisposableItem -> disposeItem()
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

    private fun disposeItem() {
        val config = DisposeItemConfig(DONT_USE_THIS_PLAYER_DATA.playerColor, item)
        compositDisposable += disposeItemUsecase.getResult(
                params = config,
                onSuccess = {
                    updateFieldItems(it)
                    reset()
                },
                onError = resetOnError()
        )
    }

    private fun updateFieldItems(result: UseItemResult) {
        val fieldGraphic = graphicController.getFieldGraphic(result.playerData.positionField.target.gamePosition)
        val itemGraphic = result.itemData.itemInfo.createGraphic()
        fieldGraphic.disposeItem(itemGraphic as DisposableItemGraphic)
        itemGraphic.itemImage.setRotating(itemGraphic, fieldGraphic.fieldImage, fieldGraphic.fieldImage.width * 0.7)

//       TODO ON PLAYER
//        val playerImage = graphicController.getPlayerGraphic(result.playerData.playerColor).playerImage
//        itemGraphic.itemImage.setRotating(itemGraphic, playerImage, playerImage.width * 0.7)
    }

}