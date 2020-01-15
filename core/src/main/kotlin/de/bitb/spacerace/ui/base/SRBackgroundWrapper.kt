package de.bitb.spacerace.ui.base

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.VisTable
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.grafik.IMAGE_PATH_WINDOW_BACKGROUND

class SRBackgroundWrapper<T : Actor>(val content: T, background: String = IMAGE_PATH_WINDOW_BACKGROUND) : VisTable(), GuiBuilder {

    init {
        add(content).expand().top()
        setBackgroundByPath(background, Dimensions.GameGuiDimensions.GAME_LABEL_WIDTH_DEFAULT * 0.7f , Dimensions.GameGuiDimensions.GAME_LABEL_WIDTH_DEFAULT * 0.7f)
        pack()
        debug = true
    }
}