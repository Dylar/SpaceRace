package de.bitb.spacerace.usecase.dispender

import de.bitb.spacerace.grafik.model.player.PlayerColor
import io.reactivex.subjects.BehaviorSubject

class PlayerColorDispenser : Dispenser<PlayerColor> {
    override val publisher: BehaviorSubject<PlayerColor> = BehaviorSubject.create()

    override fun publishUpdate(entity: PlayerColor) {
        publisher.onNext(entity)
    }
}
