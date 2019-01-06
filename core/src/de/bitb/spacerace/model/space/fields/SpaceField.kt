package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.DefaultFunction
import de.bitb.spacerace.config.DISPOSED_ITEM_SPEED
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.utils.CalculationUtils

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
    val disposedItems: MutableList<DisposableItem> = ArrayList()
    private val disposedItemsToDraw: MutableList<Image> = ArrayList()
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
                var blinkColor = color
                if (blinkTime > 0.6) {
                    blinkTime = 0f
                    blinkColor = if (color == blinkingColor) Color(1f, 1f, 1f, 1f) else blinkingColor!!
                }
                blinkColor
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

    fun disposeItem(disposableItem: DisposableItem) {
        disposedItems.add(disposableItem)
        val image = object : Image(disposableItem.img) {
            var angle = 0f
            var point = Vector2()

            val slice: Float = (2 * Math.PI / DISPOSED_ITEM_SPEED).toFloat()

            override fun act(delta: Float) {
                super.act(delta)
                angle += slice * delta
                point = CalculationUtils.calculateRotationPoint(Vector2(getAbsolutX() - width / 2, getAbsolutY() - height / 2), (width * 2).toDouble(), angle.toDouble())
            }

            override fun draw(batch: Batch?, parentAlpha: Float) {
                setPosition(point.x, point.y)
                super.draw(batch, parentAlpha)
            }
        }
        image.setOrigin(image.width / 2, image.height / 2)
        image.setPosition(getAbsolutX() - image.width / 2, getAbsolutY() - image.height / 2)
        image.color = disposableItem.owner.color

        disposedItemsToDraw.add(image)

        stage.addActor(image)

    }

    fun attachItem(disposableItem: DisposableItem) {
        disposedItems.remove(disposableItem)
        val image = disposedItemsToDraw.removeAt(0)
        image.remove()
    }

}