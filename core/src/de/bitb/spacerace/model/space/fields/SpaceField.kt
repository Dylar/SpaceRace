package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS_NR
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.groups.TestGroup

open class SpaceField(var fieldType: FieldType = FieldType.UNKNOWN) : BaseObject(fieldType.texture) {
    var id: Int = -1

    companion object {
        val NONE: SpaceField = SpaceField()
    }

    lateinit var group: SpaceGroup

    override fun getAbsolutX(): Float {
        val offset: Float = if (::group.isInitialized) group.offsetX else 0f
        return super.getX() + offset + width / 2
    }

    override fun getAbsolutY(): Float {
        val offset: Float = if (::group.isInitialized) group.offsetY else 0f
        return super.getY() + offset + height / 2
    }

    init {
        setBounds(x, y, width * 3.5f, height * 3.5f)
        setOrigin(width / 2, height / 2)
        val repeat = RepeatAction()
        repeat.action = Actions.rotateBy((Math.random() * 1).toFloat())
        repeat.count = FOREVER
        addAction(repeat)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (fieldType.color != null) {
            color = fieldType.color
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (DEBUG_FIELDS_NR) {
            val label = TextButton(id.toString(), TextureCollection.skin, "default")
            label.label.width = width
            label.setPosition(x + width / 2, y + height / 2)
//            label.setPosition(getAbsolutX(), getAbsolutY())
            label.color = Color.ROYAL
            label.style.fontColor = Color.RED
            label.draw(batch, parentAlpha)
        }
    }

}