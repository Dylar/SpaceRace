package de.bitb.spacerace.grafik.model.space.fields

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import de.bitb.spacerace.config.FIELD_ROTATION
import de.bitb.spacerace.config.ROTATION_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.blink.BlinkingImage
import de.bitb.spacerace.grafik.model.objecthandling.blink.IBlinkingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.RotatingImage

class FieldImage(
        var fieldType: FieldType
) : GameImage(),
        IBlinkingImage by BlinkingImage(),
        IRotatingImage by RotatingImage() {

    override var movingSpeed: Float = (ROTATION_SPS * Math.random()).toFloat()

    init {
        animation = fieldType.getAnimation()
        setOrigin(FIELD_BORDER / 2, FIELD_BORDER / 2)

        if (FIELD_ROTATION) {
            val repeat = RepeatAction()
            val direction = if ((Math.random() * 2).toInt() == 1) 1 else -1
            repeat.action = Actions.rotateBy((Math.random() * direction).toFloat())!!
            repeat.count = RepeatAction.FOREVER
            addAction(repeat)
        }
        idlingCount = actions.size
    }

    override fun act(delta: Float) {
        super.act(delta)
        color = getBlinkColor(color, fieldType.color ?: color)
        actRotation(this, followImage, delta)
    }

}
