package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandDispender
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        val commandDispender: CommandDispender
) : StreamUseCaseNoParams<BaseCommand> {

    override fun buildUseCaseObservable(): Observable<BaseCommand> {
        return commandDispender.publisher
                .switchMap(::handleCommand)
    }

    private fun handleCommand(command: BaseCommand) =
            Observable.fromCallable {
                Logger.justPrint("Executed handleCommand:\nPlayer: ${command.player},\nCommand: ${command::class.java.simpleName}")
                command.apply { execute() }
            }
}