package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.CommandDispender
import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        private val commandDispender: CommandDispender
) : UseCase<BaseCommand, MainGame>(),
        Dispender<BaseCommand> by commandDispender,
        DefaultFunction by object : DefaultFunction {} {

    override fun buildUseCaseObservable(params: MainGame): Observable<BaseCommand> {
        return publisher
                .flatMap { command ->
                    Completable.fromCallable {
                        Logger.println("doOnNext:\nPlayer: ${command.playerData},\nCommand: ${command::class.java.simpleName}")
                        if (command.canExecute(params)) {
                            Logger.println("Executed")
                            command.execute(params)
                        } else Logger.println("NOOOOOOOOOOOOOOOOOOT Executed")
                    }.toSingleDefault(command).toObservable()
                }
    }

}