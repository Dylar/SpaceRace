package de.bitb.spacerace.database.player

import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.subjects.BehaviorSubject

class PlayerColorDispender : Dispender<PlayerColor> {
    override val publisher: BehaviorSubject<PlayerColor> = BehaviorSubject.create()

    override fun publishUpdate(entity: PlayerColor) {
        publisher.onNext(entity)
    }
}
