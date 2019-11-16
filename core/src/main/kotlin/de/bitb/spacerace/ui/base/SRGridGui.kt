package de.bitb.spacerace.ui.base

import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisLabel
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.controller.GraphicController
import javax.inject.Inject

abstract class SRGuiGrid : GridGroup(), GuiBuilder {

    @Inject
    lateinit var graphicController: GraphicController

    init {
//        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT
    }

    protected fun setGuiBorder(
            columns: Float,
            rows: Float,
            guiPosX: Float = 0f,
            guiPosY: Float = 0f,
            alignHoriz: SRAlign = SRAlign.NONE,
            alignVert: SRAlign = SRAlign.NONE
    ) {
        width = itemWidth * columns + spacing * 2 * columns
        height = itemHeight * rows + spacing * 2 * rows

        alignGui(guiPosX, guiPosY, width, height, alignHoriz, alignVert)
    }

    protected fun addEmptySlot() {
        val emptySlot = VisLabel()
        emptySlot.width = itemWidth
        emptySlot.height = itemHeight
        addActor(emptySlot)
    }
}

enum class SRAlign {
    NONE, LEFT, RIGHT, TOP, BOTTOM
}
