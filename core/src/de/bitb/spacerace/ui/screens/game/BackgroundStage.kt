package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.background.FallingStar


class BackgroundStage(val screen: BaseScreen) : BaseStage() {

    private var texture: Texture = TextureCollection.gameBackground

    init {
        startBombarding()
    }

    private fun startBombarding() {
        for (index in 0..5) {
            val star = FallingStar(startY = (SCREEN_HEIGHT / 2).toFloat(), endY = (SCREEN_HEIGHT / 2).toFloat())
            addActor(star)
        }
    }

    override fun draw() {
        clearColor()
        batch.begin()
        setColor(alpha = 0.9f)
        batch.draw(texture, 0f, 0f, posX.toInt(), posY.toInt(),
                (SCREEN_WIDTH * (MAX_ZOOM - screen.currentZoom + 1)).toInt(),
                (SCREEN_HEIGHT * (MAX_ZOOM - screen.currentZoom + 1)).toInt())
        clearColor()
        batch.end()
        super.draw()
    }

}