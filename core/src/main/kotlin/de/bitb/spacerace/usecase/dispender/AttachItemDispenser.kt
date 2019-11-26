package de.bitb.spacerace.usecase.dispender

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import io.reactivex.subjects.BehaviorSubject

class AttachItemDispenser : Dispenser<AttachItemConfig> {
    override val publisher: BehaviorSubject<AttachItemConfig> = BehaviorSubject.create()

    override fun publishUpdate(entity: AttachItemConfig) {
        publisher.onNext(entity)
    }
}

data class AttachItemConfig(
        var playerData: PlayerData,
        var items: List<ItemData>
)