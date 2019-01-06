package de.bitb.spacerace.model.background

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.BACKGROUND_STARS_SCALE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.player.PlayerColor

class FallingStar(var gameScreen: BaseScreen,
                  var startX: Float = 0f,
                  var startY: Float = 0f,
                  var endX: Float = SCREEN_WIDTH.toFloat(),
                  var endY: Float = SCREEN_HEIGHT.toFloat())
    : BaseObject(TextureCollection.fallingStar) {

    init {
        randomColor()
    }

    override fun act(delta: Float) {
        super.act(delta)
        val zoom = (MAX_ZOOM - gameScreen.currentZoom + 1) * BACKGROUND_STARS_SCALE
        scaleX = zoom
        scaleY = zoom

        if (isIdling()) {
            randomColor()
            calculateValues()
            setPosition(startX, startY)
//            Logger.println("ROTATION: $rotation")
            moveTo(endX, endY)
        }


    }

    private fun randomColor() {
        val random: Int = (Math.random() * PlayerColor.values().size).toInt()
        color = PlayerColor.values()[random].color
    }

    private fun calculateValues() {
        movingSpeed = (Math.random() * 15f + 25).toFloat()
        startY = (Math.random() * SCREEN_HEIGHT).toFloat()
        endY = (Math.random() * SCREEN_HEIGHT).toFloat()

        val degrees = Math.atan2(
                (endY - startY).toDouble(),
                (endX - startX).toDouble()
        ) * 180.0 / Math.PI
        rotation = 120f + degrees.toFloat()
    }

}