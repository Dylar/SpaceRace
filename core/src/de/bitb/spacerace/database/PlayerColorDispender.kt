package de.bitb.spacerace.database

import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.subjects.BehaviorSubject

class PlayerColorDispender {
    val pusher: BehaviorSubject<PlayerColor> = BehaviorSubject.create()
}
