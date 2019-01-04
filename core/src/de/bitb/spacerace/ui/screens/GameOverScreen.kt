package de.bitb.spacerace.ui.screens

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection

class GameOverScreen(game: MainGame) : BaseScreen(game, null) {

    override fun createBackgroundStage(): BaseStage {
        return object : BaseStage() {
            override fun draw() {
                batch.begin()
                batch.draw(TextureCollection.gameoverBackground, 0f, 0f, SCREEN_WIDTH.toFloat(), SCREEN_HEIGHT.toFloat())
                batch.end()
                super.draw()
            }
        }
    }


}