package de.bitb.spacerace.core

import de.bitb.spacerace.events.commands.BaseCommand
import io.reactivex.subjects.BehaviorSubject

class CommandDispender : Dispender<BaseCommand> {
    override val publisher: BehaviorSubject<BaseCommand> = BehaviorSubject.create()

    override fun publishUpdate(entity: BaseCommand) {
        publisher.onNext(entity)
    }
}
