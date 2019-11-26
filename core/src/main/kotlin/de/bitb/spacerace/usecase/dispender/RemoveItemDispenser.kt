package de.bitb.spacerace.usecase.dispender

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import io.reactivex.subjects.BehaviorSubject

class RemoveItemDispenser : Dispenser<RemoveItemConfig> {
    override val publisher: BehaviorSubject<RemoveItemConfig> = BehaviorSubject.create()

    override fun publishUpdate(entity: RemoveItemConfig) {
        publisher.onNext(entity)
    }
}

data class RemoveItemConfig(
        var playerData: PlayerData? = null,
        var fieldData: FieldData? = null,
        var items: List<ItemData> = emptyList()
)