package de.bitb.spacerace.ui.player.items

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_MENU_PADDING_SPACE
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_SIZE_FONT_SMALL
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_CANCEL
import de.bitb.spacerace.config.strings.Strings.GameGuiStrings.GAME_BUTTON_USE
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ActivableItem
import de.bitb.spacerace.database.items.DisposableItem
import de.bitb.spacerace.database.items.EquipItem
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.player.UseItemCommand
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.objecthandling.getDisplayImage
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.usecase.ui.ObserveCommandUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ItemDetails(
        guiStage: GameGuiStage,
        itemMenu: ItemMenu,
        private val itemInfo: ItemInfo,
        private val playerData: PlayerData
) : BaseMenu(guiStage, itemMenu) {

    @Inject
    protected lateinit var observeCommandUsecase: ObserveCommandUsecase

    private lateinit var useBtn: TextButton
    private lateinit var unuseBtn: TextButton
    private lateinit var usedTitle: Cell<Label>

    private val itemGraphic = itemInfo.createGraphic()

    init {
        MainGame.appComponent.inject(this)
        initObserver()

        addTitle()
        addImage()
        addText()
        addButtons()
        pack()
        setPosition()
        setUsedTitle()
        setUseButton()
    }

    private fun initObserver() {
        observeCommandUsecase.observeStream { event ->
            when (event) {
                is UseItemCommand -> {
                    setUsedTitle()
                    setUseButton()
                    setUnuseButton()
                }
            }
        }
    }

    private fun addTitle() {
        usedTitle = add("-")
        setUsedTitle()
        addPaddingTopBottom(usedTitle, GAME_MENU_PADDING_SPACE)
        setFont(usedTitle.actor, GAME_SIZE_FONT_MEDIUM)
        row()
    }

    private fun addImage() {
        val cell = add(itemGraphic.getDisplayImage())
        cell.width(SCREEN_WIDTH / 4f)
        cell.height(SCREEN_HEIGHT / 4f)
    }

    private fun addText() {
        row()
        val cell = add(itemGraphic.text)
        addPaddingTopBottom(cell, GAME_MENU_PADDING_SPACE)
        setFont(cell.actor, GAME_SIZE_FONT_SMALL)
    }

    private fun setPosition() {
        x = (Dimensions.SCREEN_WIDTH - (Dimensions.SCREEN_WIDTH / 2) - width / 2)
        y = (Dimensions.SCREEN_HEIGHT - (Dimensions.SCREEN_HEIGHT / 2) - height / 2)
    }

    private fun addButtons() {
        row()

        val container = Table(skin)
        val cell = add(container)
        cell.expandX()

        if (itemInfo is EquipItem) {
            unuseBtn = createButton(name = GAME_BUTTON_USE, listener = object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    //TODO unequip command
                    EventBus.getDefault().post(UseItemCommand(itemInfo, playerController.currentPlayerData))
                    return true
                }
            })
            addButton(container, unuseBtn)
        }

        useBtn = createButton(name = GAME_BUTTON_USE, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(UseItemCommand(itemInfo, playerController.currentPlayerData))
                return true
            }
        })
        addButton(container, useBtn)

        val cancelBtn = createButton(name = GAME_BUTTON_CANCEL, listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                onBack()
                return true
            }
        })
        addButton(container, cancelBtn)
        setUsedTitle()
        setUseButton()
        setUnuseButton()
    }

    private fun addButton(container: Table, btn: TextButton) {
        val cellBtn = container.add(btn)
        cellBtn.fillX()
        addPaddingLeftRight(cellBtn)
        setFont(cellBtn.actor)
    }

    private fun setUsedTitle() {
        val inStorage = playerData.storageItems.count { it::class == itemInfo::class }
        val text = when (itemInfo) {
            is EquipItem -> {
                val equipped = playerData.equippedItems.count { it::class == itemInfo::class }
                "EQUIPPED ($equipped/$inStorage)"
            }
            else -> "STORAGE ($inStorage)"
        }
        usedTitle.actor.setText(text)
    }

    private fun setUseButton() {
        val text = when (itemInfo) {
            is EquipItem -> "EQUIP"
            is DisposableItem -> "DISPOSE"
            is ActivableItem -> "USE"
            else -> "-"
        }
        useBtn.setText(text)

    }

    private fun setUnuseButton() {
        if (::unuseBtn.isInitialized) {
            val text = when (itemInfo) {
                is EquipItem -> "UNEQUIP"
                else -> "-"
            }
            unuseBtn.setText(text)
        }
    }

}