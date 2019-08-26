package de.bitb.spacerace.model.background

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Touchable
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.MAX_ZOOM
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.BACKGROUND_STARS_SCALE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.ONE_EIGHTY_DEGREE
import de.bitb.spacerace.config.dimensions.Dimensions.ONE_TWENTY_DEGREE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.TextureAnimation
import de.bitb.spacerace.model.objecthandling.getRunnableAction
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.player.PlayerColor

class StarImage(img: Texture,
                var gameScreen: BaseScreen,
                var startX: Float = 0f,
                var startY: Float = 0f,
                var endX: Float = Dimensions.SCREEN_WIDTH,
                var endY: Float = SCREEN_HEIGHT)
    : GameImage(TextureAnimation(img))
        , IMovingImage by MovingImage() {

    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
        touchable = Touchable.disabled
        movingState = MovingState.NONE
        endX += 100f
        endY += 100f
        randomColor()
    }

    val worldRectangle: Rectangle = Rectangle(endX, endY, 1f, 1f)

    override fun act(delta: Float) {
        super.act(delta)
        val zoom = (MAX_ZOOM - gameScreen.currentZoom + 1) * BACKGROUND_STARS_SCALE
        scaleX = zoom.toFloat()
        scaleY = zoom.toFloat()

        if (movingState != MovingState.MOVING) {
            randomColor()
            calculateValues()
            setPosition(startX, startY)
            moveToPoint(this, worldRectangle,
                    getRunnableAction(Runnable { movingState = MovingState.NONE }))
            worldRectangle.y = endY
            worldRectangle.x = endX
        } else {
            actMovingTo(delta, this, worldRectangle)
        }
    }

    private fun randomColor() {
        val random: Int = (Math.random() * PlayerColor.values().size).toInt()
        color = PlayerColor.values()[random].color
    }

    private fun calculateValues() {
//        star.getGameImage().movingSpeed = (Math.random() * 15f + 25).toFloat()
        startY = (Math.random() * SCREEN_HEIGHT).toFloat()
        endY = (Math.random() * SCREEN_HEIGHT).toFloat()

        val degrees = Math.atan2(
                (endY - startY).toDouble(),
                (endX - startX).toDouble()
        ) * ONE_EIGHTY_DEGREE / Math.PI
        rotation = (ONE_TWENTY_DEGREE + degrees.toFloat()).toFloat()
//TODO mach wegen kamera plus
    }

}
