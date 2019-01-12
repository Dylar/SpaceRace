package de.bitb.spacerace.model.space.fields

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
        val direction = if ((Math.random() * 2).toInt() == 1) 1 else -1
        repeat.action = Actions.rotateBy((Math.random() * direction).toFloat())!!
        repeat.count = RepeatAction.FOREVER
        addAction(repeat)
        idlingCount = actions.size
    }

    override fun act(delta: Float) {
        super.act(delta)
        color = getBlinkColor(color, fieldType.color ?: color)
    }

}
