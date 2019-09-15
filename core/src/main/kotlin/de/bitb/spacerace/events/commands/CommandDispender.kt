package de.bitb.spacerace.events.commands

import io.reactivex.subjects.PublishSubject

class CommandDispender {
     val publisher: PublishSubject<BaseCommand> =
             PublishSubject.create()

     fun publishUpdate(entity: BaseCommand) {
        publisher.onNext(entity)
    }
}
