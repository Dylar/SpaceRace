package de.bitb.spacerace.ui.screens

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.base.CameraRenderer
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.STAR_COUNT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.model.background.FallingStar
import de.bitb.spacerace.grafik.model.objecthandling.GameImage

class BackgroundStage(
        val screen: BaseScreen,
        private var texture: Texture = TextureCollection.gameBackground,
        private var alphaValue: Float = 0.9f //0.9f
) : BaseStage() {

    private var currentZoom: Float = 0.3f
    var backgroundObjects: MutableList<GameImage> = ArrayList() //TODO

    init {
        startBombarding()
    }

    private var posX: Float = 0f
    private var posY: Float = 0f

    fun translateTo(posX: Float, posY: Float) {
        this.posX = posX
        this.posY = posY
    }

    fun translateBy(distanceX: Float, distanceY: Float) {
        posX += distanceX
        posY += distanceY
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

    override fun act(delta: Float) {
        super.act(delta)
        if (screen is CameraRenderer) {
            currentZoom = screen.currentZoom
        }

//        alphaValue = if (alphaValue < 1f) alphaValue + 0.1f * delta else 0.1f
    }

    override fun draw() {
        clearColor()
        batch.begin()
        setColor(alpha = alphaValue)
        batch.draw(texture, 0f, 0f, posX.toInt(), posY.toInt(),
                (SCREEN_WIDTH * (MAX_ZOOM - currentZoom + 1)).toInt(),
                (SCREEN_HEIGHT * (MAX_ZOOM - currentZoom + 1)).toInt())
        clearColor()
        batch.end()
        super.draw()
    }

}