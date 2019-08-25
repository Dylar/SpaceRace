package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandDispender
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveCommandUsecase @Inject constructor(
        private val commandDispender: CommandDispender
) : StreamUseCaseNoParams<BaseCommand> {

    override fun buildUseCaseObservable(): Observable<BaseCommand> {
        return commandDispender.publisher
    }

}