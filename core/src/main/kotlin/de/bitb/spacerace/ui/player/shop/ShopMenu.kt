package de.bitb.spacerace.ui.player.shop

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_ITEM_WIDTH_MIN
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_SHOP_TITLE
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.objecthandling.getDisplayImage
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage

class ShopMenu(guiStage: GameGuiStage) : BaseMenu(guiStage) {
    override fun refreshMenu() {

    }

    private lateinit var shopDetails: ShopDetails

    init {
        val items = ItemInfo.getAllItems()
        var size = items.size
        size = if (size < GAME_MENU_ITEM_WIDTH_MIN) GAME_MENU_ITEM_WIDTH_MIN else size

        addTitle(size)
        addItems(items)
        addButtons(size)

        pack()

        setPosition()
    }

    private fun setPosition() {
        x = (SCREEN_WIDTH - (SCREEN_WIDTH / 2) - width / 2)
        y = (SCREEN_HEIGHT - (SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addTitle(size: Int) {
        val cell = add(GAME_SHOP_TITLE)
        setFont(cell.actor)
        cell.colspan(size)
    }

    private fun addItems(itemGraphics: MutableList<ItemGraphic>) {
        row()
        for (item in itemGraphics) {
            val displayImage = item.getDisplayImage()
            displayImage.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    shopDetails = ShopDetails(
                            guiStage,
                            this@ShopMenu,
                            item.itemType,
                            player)
                    shopDetails.openMenu(player!!)
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