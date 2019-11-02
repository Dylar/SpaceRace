package de.bitb.spacerace.usecase.dispender

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import io.reactivex.subjects.BehaviorSubject

class MoveItemDispenser : Dispenser<MoveItemConfig> {
    override val publisher: BehaviorSubject<MoveItemConfig> = BehaviorSubject.create()

    override fun publishUpdate(entity: MoveItemConfig) {
        publisher.onNext(entity)
    }
}

data class MoveItemConfig(
        var fromField: FieldData,
        var toField: FieldData,
        var items: ItemData
)