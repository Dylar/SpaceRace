package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage

open class SpaceField(
        val fieldType: FieldType = FieldType.UNKNOWN,
        val fieldImage: FieldImage = FieldImage(fieldType),
        positionData: PositionData = PositionData())
    : GameObject(positionData),
        IBlinkingImage by fieldImage {

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

    val connections: MutableList<SpaceConnection> = ArrayList()

    val disposedItems: MutableList<DisposableItem> = ArrayList()

    override fun getGameImage(): GameImage {
        return fieldImage
    }

    init {
        setBounds(positionData.posX, positionData.posY, FIELD_BORDER, FIELD_BORDER)
    }

    fun disposeItem(disposableItem: DisposableItem) {
        disposedItems.add(disposableItem)
        getGameImage().stage.addActor(disposableItem.getGameImage())
    }

    fun attachItem(disposableItem: DisposableItem) {
        disposedItems.remove(disposableItem)
    }

    fun hasConnectionTo(spaceField: SpaceField): Boolean {
        return connections.any { it.isConnection(this, spaceField) }
    }

}