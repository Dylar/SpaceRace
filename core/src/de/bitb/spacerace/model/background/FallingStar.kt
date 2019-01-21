package de.bitb.spacerace.model.background

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

class FallingStar(gameScreen: BaseScreen,
                  startX: Float = 0f,
                  startY: Float = 0f,
                  endX: Float = SCREEN_WIDTH,
                  endY: Float = SCREEN_HEIGHT)
    : GameObject(PositionData(startX, startY, endX, endY)) {

    private val startImage: StarImage = StarImage(TextureCollection.fallingStar, gameScreen, this, startX, startY, endX, endY)

    override fun getGameImage(): GameImage {
        return startImage
    }

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
    }

}