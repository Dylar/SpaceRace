package de.bitb.spacerace.model.background

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.BACKGROUND_STARS_SCALE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.player.PlayerColor

class StarImage(img: Texture,
                var gameScreen: BaseScreen,
                var star: FallingStar,
                var startX: Float = 0f,
                var startY: Float = 0f,
                var endX: Float = Dimensions.SCREEN_WIDTH,
                var endY: Float = SCREEN_HEIGHT)
    : GameImage(img), IMovingImage by MovingImage() {

    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    init {
        touchable = Touchable.disabled

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
            moveTo(star.getGameImage(), PositionData(endX + Dimensions.SCREEN_WIDTH * 0.1f, endY))
        }
    }

    private fun randomColor() {
        val random: Int = (Math.random() * PlayerColor.values().size).toInt()
        color = PlayerColor.values()[random].color
    }

    private fun calculateValues() {
        star.getGameImage().movingSpeed = (Math.random() * 15f + 25).toFloat()
        startY = (Math.random() * SCREEN_HEIGHT).toFloat()
        endY = (Math.random() * SCREEN_HEIGHT).toFloat()

        val degrees = Math.atan2(
                (endY - startY).toDouble(),
                (endX - startX).toDouble()
        ) * 180.0 / Math.PI
        rotation = 120f + degrees.toFloat()
    }

}
