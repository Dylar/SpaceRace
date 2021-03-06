package de.bitb.spacerace.grafik.model.background

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.ITEM_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.GameObject
import de.bitb.spacerace.grafik.model.objecthandling.PositionData

class FallingStar(
        gameScreen: BaseScreen,
        startX: Float = 0f,
        startY: Float = 0f,
        endX: Float = SCREEN_WIDTH,
        endY: Float = SCREEN_HEIGHT
) : GameObject(PositionData(startX, startY, endX, endY)) {

    private val startImage: StarImage = StarImage(TextureCollection.fallingStar, gameScreen, startX, startY, endX, endY)

    override fun getGameImage(): GameImage {
        return startImage
    }

    init {
        setBounds(0f, 0f, ITEM_BORDER, ITEM_BORDER)
    }

}