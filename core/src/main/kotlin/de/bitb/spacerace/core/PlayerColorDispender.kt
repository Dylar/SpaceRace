package de.bitb.spacerace.core

import de.bitb.spacerace.base.Dispender
import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.subjects.BehaviorSubject

class PlayerColorDispender : Dispender<PlayerColor> {
    override val publisher: BehaviorSubject<PlayerColor> = BehaviorSubject.create()

    override fun publishUpdate(entity: PlayerColor) {
        publisher.onNext(entity)
    }
}
