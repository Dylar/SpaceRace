package de.bitb.spacerace.model.space.fields

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.groups.SpaceGroup

open class SpaceField(val fieldType: FieldType = FieldType.UNKNOWN, positionData: PositionData = PositionData()) :
        GameImage(positionData, fieldType.texture) {

    companion object {
        val NONE: SpaceField = SpaceField()

        fun createField(fieldType: FieldType): SpaceField {
            var positionData = PositionData()
            return when (fieldType) {
                FieldType.MINE -> MineField(positionData)
                FieldType.RANDOM -> createField(FieldType.values()[(Math.random() * FieldType.values().size).toInt()])
                else -> {
                    SpaceField(fieldType, positionData)
                }
            }
        }
    }

    var group: SpaceGroup? = null
    val connections: MutableList<SpaceConnection> = ArrayList()

    val disposedItems: MutableList<DisposableItem> = ArrayList()

    init {
        val width = FIELD_BORDER
        val height = FIELD_BORDER
        image.setOrigin(width / 2, height / 2)
        setBounds(positionData.posX, positionData.posY, width, height)

        val repeat = RepeatAction()
        repeat.action = Actions.rotateBy((Math.random() * 1).toFloat())!!
        repeat.count = FOREVER
        addAction(repeat)
    }

//    override fun act(delta: Float) {
//        super.act(delta)
//        val blinkColor: Color? = getBlinkColor(delta, color)
//        color = when {
//            blinkColor != null -> blinkColor
//            fieldType.color != null -> fieldType.color
//            else -> color
//        }
//    }
//
//    override fun draw(batch: Batch?, parentAlpha: Float) {
//        super.draw(batch, parentAlpha)
//
//    }

    fun disposeItem(disposableItem: DisposableItem) {
        disposedItems.add(disposableItem)
        disposableItem.gameImage!!.fieldPosition = this
        image.stage.addActor(disposableItem.gameImage)
    }

    fun attachItem(disposableItem: DisposableItem) {
        disposedItems.remove(disposableItem)
        disposableItem.gameImage!!.remove()
    }

    fun hasConnectionTo(spaceField: SpaceField): Boolean {
        connections.forEach {
            if (it.isConnection(this, spaceField)) return true
        }
        return false
    }

}