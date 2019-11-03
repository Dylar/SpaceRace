package de.bitb.spacerace.usecase.dispender

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import io.reactivex.subjects.BehaviorSubject

class MoveItemDispenser : Dispenser<List<MoveItemConfig>> {
    override val publisher: BehaviorSubject<List<MoveItemConfig>> = BehaviorSubject.create()

    override fun publishUpdate(entity: List<MoveItemConfig>) {
        publisher.onNext(entity)
    }
}

data class MoveItemConfig(
        var fromField: FieldData,
        var toField: FieldData,
        var item: ItemData
)