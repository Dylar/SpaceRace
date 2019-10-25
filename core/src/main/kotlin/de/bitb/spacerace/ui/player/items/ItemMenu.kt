package de.bitb.spacerace.ui.player.items

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_ITEM_WIDTH_MIN
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_MENUITEM_TITLE
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.objecthandling.getDisplayImage
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage

class ItemMenu(
        guiStage: GameGuiStage,
        private val playerData: PlayerData
) : BaseMenu(guiStage) {

    private lateinit var itemDetailsMenu: ItemDetailsMenu

    init {
        val items = graphicController.getStorageItemMap(playerData)
        var size = items.size
        size = if (size < GAME_MENU_ITEM_WIDTH_MIN) GAME_MENU_ITEM_WIDTH_MIN else size

        addTitle(size)
        addItems(items)
        addButtons(size)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = SCREEN_WIDTH / 2 - width / 2
        y = SCREEN_HEIGHT / 2 - height / 2
    }

    private fun addTitle(size: Int) {
        val cell = add(GAME_MENUITEM_TITLE)
        setFont(cell.actor)
        cell.colspan(size)
    }

    private fun addItems(items: Map<ItemType, ItemGraphic>) {
        row()
        for (typeList in items) {
            val itemType = typeList.key
            val displayImage = typeList.value.getDisplayImage()
            displayImage.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    itemDetailsMenu = ItemDetailsMenu(guiStage, this@ItemMenu, itemType, playerData)
                    itemDetailsMenu.openMenu()
                    return true
                }
            })
            add(displayImage)
        }
    }

    private fun addButtons(size: Int) {
        row()
        val cancelBtn = createButton(name = Strings.GameGuiStrings.GAME_BUTTON_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                closeMenu()
                return true
            }
        })
        val cellBtn = add(cancelBtn)
        cellBtn.colspan(size)
        setFont(cellBtn.actor)
    }

}