package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.events.commands.CommandDispender
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        val commandDispender: CommandDispender
) : StreamUseCaseNoParams<BaseCommand> {

    override fun buildUseCaseObservable(): Observable<BaseCommand> {
        return commandDispender.publisher
//                .switchMap(::updatePlayerData)
                .switchMap(::handleCommand)
    }
//
//    private fun updatePlayerData(command: BaseCommand) =
//            if (command.DONT_USE_THIS_PLAYER_DATA != NONE_PLAYER_DATA) {
//                playerDataSource
//                        .getByColor(command.DONT_USE_THIS_PLAYER_DATA.playerColor)
//                        .map { command.apply { DONT_USE_THIS_PLAYER_DATA = it.first() } }
//            } else {
//                Single.just(command)
//            }.toObservable()

    private fun handleCommand(command: BaseCommand) =
            Completable.fromCallable {
                if (command.canExecute()) {
                    Logger.printLog("Executed handleCommand:\nPlayer: ${command.DONT_USE_THIS_PLAYER_DATA},\nCommand: ${command::class.java.simpleName}")
                    command.execute()
                }
//                else Logger.println("Not Executed handleCommand:\nPlayer: ${command.DONT_USE_THIS_PLAYER_DATA},\nCommand: ${command::class.java.simpleName}")
            }.toSingleDefault(command).toObservable()

}