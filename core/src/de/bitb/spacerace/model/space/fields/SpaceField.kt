package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage
import de.bitb.spacerace.model.space.groups.SpaceGroup

open class SpaceField(val fieldType: FieldType = FieldType.UNKNOWN, var fieldImage: FieldImage = FieldImage(fieldType.texture, fieldType), positionData: PositionData = PositionData()) :
        GameObject(positionData), IBlinkingImage by fieldImage {

    override fun getGameImage(): GameImage {
        return fieldImage
    }

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

    var group: SpaceGroup? = null
    val connections: MutableList<SpaceConnection> = ArrayList()

    val disposedItems: MutableList<DisposableItem> = ArrayList()

    init {
        setBounds(positionData.posX, positionData.posY, FIELD_BORDER, FIELD_BORDER)
    }

    fun disposeItem(disposableItem: DisposableItem) {
        disposedItems.add(disposableItem)
        getGameImage().stage.addActor(disposableItem.getGameImage())
    }

    fun attachItem(disposableItem: DisposableItem) {
        disposedItems.remove(disposableItem)
        disposableItem.getGameImage().remove()
    }

    fun hasConnectionTo(spaceField: SpaceField): Boolean {
        connections.forEach {
            if (it.isConnection(this, spaceField)) return true
        }
        return false
    }

}