package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.BLACK
import com.badlogic.gdx.graphics.Color.YELLOW
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.groups.SpaceGroup

open class SpaceField(val fieldType: FieldType = FieldType.UNKNOWN) : BaseObject(fieldType.texture) {

    companion object {
        val NONE: SpaceField = SpaceField()

        fun createField(fieldType: FieldType): SpaceField {
            return when (fieldType) {
                FieldType.MINE -> MineField()
                FieldType.RANDOM -> createField(FieldType.values()[(Math.random() * FieldType.values().size).toInt()])
                else -> {
                    SpaceField(fieldType)
                }
            }
        }
    }

    var id: Int = -1
    lateinit var group: SpaceGroup
    val disposedItems: MutableList<Item> = ArrayList()
    var update = false
    var blinkingColor: Color? = null

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
        repeat.action = Actions.rotateBy((Math.random() * 1).toFloat())!!
        repeat.count = FOREVER
        addAction(repeat)
    }

    private var blinkTime = 0f
    override fun act(delta: Float) {
        super.act(delta)
        color = when {
            blinkingColor != null -> {
                blinkTime += delta
                if (blinkTime > 0.5) {
                    blinkTime = 0f
                    val mod = if (blinkingColor == fieldType.color) 0.6f else 0.0f
                    blinkingColor!!.g = fieldType.color!!.g + mod
                    blinkingColor!!.r = fieldType.color.r + mod
                    blinkingColor!!.b = fieldType.color.b + mod
                }
                blinkingColor
            }
            fieldType.color != null -> fieldType.color
            else -> color
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