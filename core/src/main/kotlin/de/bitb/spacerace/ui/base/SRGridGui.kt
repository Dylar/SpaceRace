package de.bitb.spacerace.ui.base

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.core.controller.GraphicController
import javax.inject.Inject

open class SRGuiGrid : GridGroup(), GuiBuilder {

    @Inject
    lateinit var graphicController: GraphicController

    init {
//        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT
    }

    fun setGuiBorder(
            columns: Float,
            rows: Float,
            guiPosX: Float = 0f,
            guiPosY: Float = 0f,
            alignHoriz: SRAlign = SRAlign.NONE,
            alignVert: SRAlign = SRAlign.NONE
    ) {
        width = (itemWidth + spacing * 2) * columns
        height = (itemHeight + spacing * 2) * rows

        alignGui(guiPosX, guiPosY, width, height, alignHoriz, alignVert)
    }

    protected fun addEmptySlot() {
        val emptySlot = VisLabel()
        emptySlot.width = itemWidth
        emptySlot.height = itemHeight
        addActor(emptySlot)
    }

    fun <T> addItems(
            items: List<T>,
            width: Float = GAME_BUTTON_HEIGHT_DEFAULT,
            height: Float = GAME_BUTTON_HEIGHT_DEFAULT,
            spacing: Float = 20f,
            createItemView: (T) -> Actor
    ) = this.apply {
        setItemSize(width, height)
        val rows = 3f % items.size
        val column = if (items.size < 3) items.size.toFloat() else 3f
        this.spacing = spacing
        setGuiBorder(columns = column, rows = rows)

        items.forEach {
            val gridItem = createItemView(it)
            addActor(gridItem)
        }
    }
}

enum class SRAlign {
    NONE, LEFT, RIGHT, TOP, BOTTOM
}
