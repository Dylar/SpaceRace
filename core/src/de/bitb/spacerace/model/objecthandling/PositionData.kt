package de.bitb.spacerace.model.objecthandling

import de.bitb.spacerace.config.MOVING_SPEED
import de.bitb.spacerace.model.space.fields.SpaceField

data class PositionData(var posX: Float = 0f,
                        var posY: Float = 0f,
                        var width: Float = 0f,
                        var height: Float = 0f) {

    var movingSpeed: Float = MOVING_SPEED

    fun setPosition(spaceField: PositionData) {
//        var offset: Float = spaceField.group?.offsetX ?: 0f
//        posX = spaceField.posX + offset + spaceField.width / 2

//        offset = spaceField.group?.offsetY ?: 0f
//        posY = spaceField.posY + offset + spaceField.height / 2

        posX = spaceField.posX
        posY = spaceField.posY
    }
}