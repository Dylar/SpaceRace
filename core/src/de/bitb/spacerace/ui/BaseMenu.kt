package de.bitb.spacerace.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace

abstract class BaseMenu<I : MenuItem>(val space: BaseSpace, guiStage: BaseGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage {

    protected var itemTable: Table

    val tableContainer = Container<Table>()

    val screenWidth = Gdx.graphics.width.toFloat()
    val screenHeight = Gdx.graphics.height.toFloat()
    val windowWidth = screenWidth - (BaseGuiStage.slotWidth + BaseGuiStage.singlePadding) * 3
    val windowHeight = screenHeight * 0.5f

    init {
        isVisible = false
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        val items = space.currentShip.items
        val size = items.size + 1

        tableContainer.setSize(windowWidth, windowHeight)
        tableContainer.setPosition((screenWidth - windowWidth) / 2.0f, (screenHeight - windowHeight) / 2.0f)
        tableContainer.fillX()

        val topLabel = Label(getTitle(), skin)
        topLabel.setAlignment(Align.center)
        row().colspan(size).expandX().fillX()
        add(topLabel).fillX()

        row().colspan(1).expandX().fillX()

        itemTable = Table(skin)
//        populateItems(items)
        add(itemTable)
        row().colspan(size).expandX().fillX()

        val buttonTable = Table(skin)
        buttonTable.pad(BaseGuiStage.slotHeight)
        createButtons(buttonTable)
        add(buttonTable).colspan(size).fillX()

        tableContainer.actor = this
        guiStage.addActor(tableContainer)
    }

    abstract fun getTitle(): String

    fun populateItems(items: MutableList<I>) {
        for (item in items.withIndex()) {
            val image1 = TextureRegionDrawable(TextureRegion(item.value.getImage()))
            val btn = createImageButton(image1, image1, image1, listener = object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    populateItem(item.value)
                    return true
                }
            })
            itemTable.add(btn)
        }
        itemTable.invalidate()
    }

    open fun populateItem(item: I) {

    }

    abstract fun createButtons(buttonTable: Table)

    fun toggle() {
        isVisible = when {
            isVisible -> {
                itemTable.clearChildren()
                false
            }
            else -> {
                onVisible()
                true
            }
        }
    }

    abstract fun onVisible()
}