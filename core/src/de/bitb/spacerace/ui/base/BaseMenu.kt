package de.bitb.spacerace.ui.base

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.Dimensions.GameDimensions.singlePadding
import de.bitb.spacerace.config.Dimensions.GameDimensions.slotHeight
import de.bitb.spacerace.config.Dimensions.GameDimensions.slotWidth
import de.bitb.spacerace.config.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace

abstract class BaseMenu<I : MenuItem>(val space: BaseSpace, guiStage: BaseGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage {

    protected var contentTable: Table
    protected var titleLabel: Label
    val size = 3

    val screenWidth = SCREEN_WIDTH.toFloat()
    val screenHeight = SCREEN_HEIGHT.toFloat()
    val windowWidth: Float = (screenWidth - (slotWidth + singlePadding) * 2.8).toFloat()
    val windowHeight = screenHeight * 0.5f

    init {
        isVisible = false
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val tableContainer = Container<Table>()
        tableContainer.setSize(windowWidth, windowHeight)
        tableContainer.setPosition((screenWidth - windowWidth) / 2.0f, (screenHeight - windowHeight) / 2.0f)
        tableContainer.fillX()

        titleLabel = createTitle()
        contentTable = createContent()
        createButtons()

        tableContainer.actor = this
        guiStage.addActor(tableContainer)
    }

    private fun createContent(): Table {
        val contentTable = Table(skin)
        add(contentTable)
        row().colspan(size).expandX().fillX()
        return contentTable
    }

    private fun createButtons() {
        val buttonTable = Table(skin)
        buttonTable.pad(slotHeight)
        populateButtons(buttonTable)
        add(buttonTable).colspan(size).fillX()
    }

    abstract fun populateButtons(buttonTable: Table)

    private fun createTitle(): Label {
        val titleLabel = createLabel(getTitle())
        titleLabel.setAlignment(Align.center)
        row().colspan(size).expandX().fillX()
        add(titleLabel).fillX()

        row().colspan(1).expandX().fillX()
        return titleLabel
    }

    abstract fun getTitle(): String

    fun populateItems(items: MutableList<I>) {
        for (item in items.withIndex()) {
            val image1 = TextureRegionDrawable(TextureRegion(item.value.getImage()))
            image1.tint(item.value.getTintColor())
            val btn = createImageButton(image1, image1, image1, listener = object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    populateItem(item.value)
                    return true
                }
            })
            contentTable.add(btn)
        }
        contentTable.invalidate()
    }

    open fun populateItem(item: I) {

    }


    fun toggle() {
        isVisible = when {
            isVisible -> {
                clearView()
                false
            }
            else -> {
                titleLabel.setText(getTitle())
                onVisible()
                true
            }
        }
    }

    private fun clearView() {
        contentTable.clearChildren()
    }

    abstract fun onVisible()
}