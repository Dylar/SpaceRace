package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.blink.BlinkingImage
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage

class FieldImage(img: Texture, var fieldType: FieldType) : GameImage(img), IBlinkingImage by BlinkingImage() {
    init {
        setOrigin(FIELD_BORDER / 2, FIELD_BORDER / 2)

        val repeat = RepeatAction()
        repeat.action = Actions.rotateBy((Math.random() * 1).toFloat())!!
        repeat.count = RepeatAction.FOREVER
        addAction(repeat)
    }

    override fun act(delta: Float) {
        super.act(delta)
        val blinkColor: Color? = getBlinkColor(delta, color)
        color = when {
            blinkColor != null -> blinkColor
            fieldType.color != null -> fieldType.color
            else -> color
        }
    }

}
