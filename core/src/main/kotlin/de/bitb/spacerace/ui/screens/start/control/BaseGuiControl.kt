package de.bitb.spacerace.ui.screens.start.control

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.ui.base.GuiComponent
import de.bitb.spacerace.ui.screens.start.StartGuiStage

abstract class BaseGuiControl(val guiStage: StartGuiStage) : Table(TextureCollection.skin), GuiComponent by guiStage {

    init {
        background = TextureRegionDrawable(TextureRegion(TextureCollection.guiBackground))

        pack()

    }

}