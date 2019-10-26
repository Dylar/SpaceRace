package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemGraphic
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.blink.IBlinkingImage

val NONE_SPACE_FIELD: FieldGraphic = FieldGraphic().apply { id = -1 }

open class FieldGraphic(
        val fieldType: FieldType = FieldType.RANDOM,
        val fieldImage: FieldImage = FieldImage(fieldType)
) : GameObject(PositionData()),
        IBlinkingImage by fieldImage {

    companion object {

        fun createField(fieldType: FieldType): FieldGraphic {
            return when (fieldType) {
//                FieldType.MINE -> MineField()
                FieldType.RANDOM
                -> createField(FieldType.values()[(Math.random() * FieldType.values().size).toInt()])
                else -> FieldGraphic(fieldType)
            }
        }
    }

    val disposedItems: MutableList<ItemGraphic> = ArrayList()

    override fun getGameImage(): GameImage {
        return fieldImage
    }

    init {
        setBounds(gamePosition.posX, gamePosition.posY, FIELD_BORDER, FIELD_BORDER)
    }

    fun addItem(disposableItem: ItemGraphic) {
        disposedItems.add(disposableItem)
        getGameImage().stage.addActor(disposableItem.getGameImage())
    }

    fun removeItem(disposableItem: ItemGraphic) {
        disposedItems.remove(disposableItem)
//        disposableItem.getGameImage().remove()
    }
}