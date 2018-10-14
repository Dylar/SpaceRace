package de.bitb.spacerace.model

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType

class SpaceField(var id: Int = -1,var fieldType: FieldType = FieldType.UNKNOWN, img: Texture = fieldType.texture) : BaseObject(img) {
    val connections: MutableList<SpaceField> = ArrayList()

    init {
        setBounds(x, y, width * 1.4f, height * 1.4f)
        setOrigin(width / 2, height / 2)
        val repeat = RepeatAction()
        repeat.action = Actions.rotateBy((Math.random() * 1).toFloat())
        repeat.count = FOREVER
        addAction(repeat)
    }

    fun hasConnectionTo(spaceField: SpaceField): Boolean {
        return connections.contains(spaceField)
    }
}