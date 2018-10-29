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
import de.bitb.spacerace.model.Ship
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.BaseSpace

class NextTurnMenu(space: BaseSpace, guiStage: BaseGuiStage) : BaseMenu<Ship>(space, guiStage) {
    override fun getTitle(): String {
        return "Last turn"
    }
//
//    private var itemTable: Table
////    private var itemText: Table
////    private var itemImage: Table
//
//    val tableContainer = Container<Table>()
//
//    val screenWidth = Gdx.graphics.width.toFloat()
//    val screenHeight = Gdx.graphics.height.toFloat()
//
//    init {
//        isVisible = false
//        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))
//
//        val items = space.ships
//        val size = items.size + 1
//
//        val windowWidth = screenWidth * 0.7f
//        val windowHeight = screenHeight * 0.5f
//
//        tableContainer.setSize(windowWidth, windowHeight)
//        tableContainer.setPosition((screenWidth - windowWidth) / 2.0f, (screenHeight - windowHeight) / 2.0f)
//        tableContainer.fillX()
//
//        val topLabel = Label("Last turn", skin)
//        topLabel.setAlignment(Align.center)
//        row().colspan(size).expandX().fillX()
//        add(topLabel).fillX()
//
//        val slider = Slider(0f, 100f, 1f, false, skin)
//        row().colspan(size).expandX().fillX()
//        add(slider).fillX().row()
//
//        row().colspan(1).expandX().fillX()
//
//        itemTable = Table(skin)
////        populateItems(space.ships)
//        add(itemTable)
//        row().colspan(size).expandX().fillX()
//
//        val buttonTable = Table(skin)
//

//    createButtons

//        add(buttonTable).colspan(size).fillX()
//
//        tableContainer.actor = this
//        guiStage.addActor(tableContainer)
//    }
//
//    private fun populateItems(items: MutableList<MenuItem>) {
//        for (item in items.withIndex()) {
//            val image1 = TextureRegionDrawable(TextureRegion(item.value.getImage()))
//            val btn = createImageButton(image1, image1, image1, listener = object : InputListener() {
//                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//                    populateItem(item.value)
//                    return true
//                }
//            })
//            itemTable.add(btn)
//        }
//        itemTable.invalidate()
//    }
//
//    private fun populateItem(item: MenuItem) {
//
//    }

    override fun createButtons(buttonTable: Table) {
        val buttonA = createButton(name = "Go on", listener = object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                toggle()
                space.nextPhase()
                return true
            }
        })
        buttonTable.row().fillX().expandX()
        buttonTable.add(buttonA).width(windowWidth / 4.0f)
    }

    override fun onVisible() {
        populateItems(space.ships)
    }
}