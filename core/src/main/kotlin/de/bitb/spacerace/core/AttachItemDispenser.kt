package de.bitb.spacerace.core

import de.bitb.spacerace.base.Dispenser
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.subjects.BehaviorSubject

class AttachItemDispenser : Dispenser<AttachItemConfig> {
    override val publisher: BehaviorSubject<AttachItemConfig> = BehaviorSubject.create()

    override fun publishUpdate(entity: AttachItemConfig) {
        publisher.onNext(entity)
    }
}

data class AttachItemConfig(
        var playerData: PlayerData,
        var list: List<ItemData>
)