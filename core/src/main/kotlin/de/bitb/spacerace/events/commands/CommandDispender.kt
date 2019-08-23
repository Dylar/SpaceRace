package de.bitb.spacerace.events.commands

import de.bitb.spacerace.events.commands.BaseCommand
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class CommandDispender {
     val publisher: PublishSubject<BaseCommand> =
             PublishSubject.create()

     fun publishUpdate(entity: BaseCommand) {
        publisher.onNext(entity)
    }
}
