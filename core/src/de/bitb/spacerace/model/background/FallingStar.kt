package de.bitb.spacerace.model.background

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

class FallingStar(var gameScreen: BaseScreen,
                  var startX: Float = 0f,
                  var startY: Float = 0f,
                  var endX: Float = SCREEN_WIDTH.toFloat(),
                  var endY: Float = SCREEN_HEIGHT.toFloat())
    : GameObject(PositionData(startX, startY, endX, endY)){
    override fun getGameImage(): GameImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
//        randomColor()
    }

//    override fun act(delta: Float) {
//        super.act(delta)
//        val zoom = (MAX_ZOOM - gameScreen.currentZoom + 1) * BACKGROUND_STARS_SCALE
//        scaleX = zoom
//        scaleY = zoom
//
//        if (actions.isEmpty) {
//            randomColor()
//            calculateValues()
//            setPosition(startX, startY)
//            moveTo(this, endX, endY)
//        }
//
//
//    }
//
//    private fun randomColor() {
//        val random: Int = (Math.random() * PlayerColor.values().size).toInt()
//        color = PlayerColor.values()[random].color
//    }
//
//    private fun calculateValues() {
//        rotationPoint.movingSpeed = (Math.random() * 15f + 25).toFloat()
//        startY = (Math.random() * SCREEN_HEIGHT).toFloat()
//        endY = (Math.random() * SCREEN_HEIGHT).toFloat()
//
//        val degrees = Math.atan2(
//                (endY - startY).toDouble(),
//                (endX - startX).toDouble()
//        ) * 180.0 / Math.PI
//        rotation = 120f + degrees.toFloat()
//    }

}