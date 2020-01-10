package de.bitb.spacerace.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.TextureCollection

class GameOverScreen(game: MainGame) : BaseScreen(game, null) {

    private val simpleStage =
            object : BaseStage() {
                override fun draw() {
                    batch.begin()
                    batch.draw(TextureCollection.gameOverBackground, 0f, 0f, SCREEN_WIDTH, SCREEN_HEIGHT)
                    batch.end()
                    super.draw()
                }
            }
    override var allStages: List<Stage> = listOf(simpleStage)
    override var inputStages: List<Stage> = listOf()

}