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
import de.bitb.spacerace.usecase.UseCaseWithoutParams
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val commandDispender: CommandDispender
) : UseCaseWithoutParams<BaseCommand>(),
        Dispender<BaseCommand> by commandDispender,
        DefaultFunction by DEFAULT {

    override fun buildUseCaseObservable(): Observable<BaseCommand> {
        return publisher
                .switchMap(::updatePlayerData)
                .flatMap(::handleCommand)
    }

    private fun updatePlayerData(command: BaseCommand) =
            if (command.playerData != NONE_PLAYER_DATA) {
                playerDataSource
                        .getByColor(command.playerData.playerColor)
                        .map { command.apply { playerData = it.first() } }
            } else {
                Single.just(command)
            }.toObservable()

    private fun handleCommand(command: BaseCommand) =
            Completable.fromCallable {
                if (command.canExecute()) {
                    Logger.println("Executed doOnNext:\nPlayer: ${command.playerData},\nCommand: ${command::class.java.simpleName}")
                    command.execute()
                } else Logger.println("Not Executed doOnNext:\nPlayer: ${command.playerData},\nCommand: ${command::class.java.simpleName}")
            }.toSingleDefault(command).toObservable()

}