package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.CommandDispender
import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val commandDispender: CommandDispender
) : UseCase<BaseCommand, MainGame>(),
        Dispender<BaseCommand> by commandDispender,
        DefaultFunction by DEFAULT {

    override fun buildUseCaseObservable(params: MainGame): Observable<BaseCommand> {
        return publisher
                .switchMap { command ->
                    if (command.playerData != NONE_PLAYER_DATA) {
                        playerDataSource.getByColor(command.playerData.playerColor)
                                .map { command.apply { playerData = it.first() } }
                    } else {
                        Single.just(command)
                    }.toObservable()
                }
                .flatMap { command ->
                    Completable.fromCallable {
                        if (command.canExecute(params)) {
                            Logger.println("doOnNext:\nPlayer: ${command.playerData},\nCommand: ${command::class.java.simpleName}")
                            command.execute(params)
                        } else Logger.println("Error doOnNext:\nPlayer: ${command.playerData},\nCommand: ${command::class.java.simpleName}")
                    }.toSingleDefault(command).toObservable()
                }
    }

}