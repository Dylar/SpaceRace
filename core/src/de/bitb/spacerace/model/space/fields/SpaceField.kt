package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.SpaceGroup

open class SpaceField(var fieldType: FieldType = FieldType.UNKNOWN) : BaseObject(fieldType.texture) {

    companion object {
        val NONE: SpaceField = SpaceField()

        fun createField(fieldType: FieldType): SpaceField {
            return when (fieldType) {
                FieldType.WIN -> SpaceField(FieldType.WIN)
                FieldType.LOSE -> SpaceField(FieldType.LOSE)
                FieldType.SHOP -> SpaceField(FieldType.SHOP)
                FieldType.GIFT -> SpaceField(FieldType.GIFT)
                FieldType.AMBUSH -> SpaceField(FieldType.AMBUSH)
                FieldType.MINE -> MineField()
                FieldType.RANDOM -> createField(FieldType.values()[(Math.random() * FieldType.values().size).toInt()])
                FieldType.UNKNOWN -> createField(FieldType.values()[(Math.random() * FieldType.values().size).toInt()])
            }
        }
    }

    var id: Int = -1
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
        if (DEBUG_FIELDS) {
            val label = TextButton(id.toString() + " " + fieldType.name, TextureCollection.skin, "default")
            label.label.width = width
            label.setPosition(x + width / 2, y + height / 2)
//            label.setPosition(getAbsolutX(), getAbsolutY())
            label.color = Color.ROYAL
            label.style.fontColor = Color.RED
            label.draw(batch, parentAlpha)
        }
    }

}