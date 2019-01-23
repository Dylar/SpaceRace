package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.STAR_COUNT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.background.FallingStar
import de.bitb.spacerace.model.objecthandling.GameImage


class BackgroundStage(val screen: BaseScreen, private var texture: Texture = TextureCollection.gameBackground) : BaseStage() {

    var backgroundObjects: MutableList<GameImage> = ArrayList()

    init {
        startBombarding()
    }

    override fun translateBy(distanceX: Float, distanceY: Float) {
        super.translateBy(distanceX, distanceY)
        backgroundObjects.forEach {
            it.x -= distanceX
            it.y += distanceY
        }
    }

    private fun startBombarding() {
        for (index in 1..STAR_COUNT) {
            val star = FallingStar(screen)
            addActor(star.getGameImage())
        }
    }

    fun addActor(actor: GameImage) {
        backgroundObjects.add(actor)
        super.addActor(actor)
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