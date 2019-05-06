package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.CommandDispender
import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.UpdatePlayerUsecase
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class CommandUsecase @Inject constructor(
        private val updatePlayerUsecase: UpdatePlayerUsecase,
        private val playerChangedUsecase: PlayerChangedUsecase,
        private val commandDispender: CommandDispender
) : UseCase<BaseCommand, MainGame>(),
        Dispender<BaseCommand> by commandDispender,
        DefaultFunction by object : DefaultFunction {} {

    override fun buildUseCaseObservable(params: MainGame): Observable<BaseCommand> {
        return publisher
                .doOnNext {
                    Logger.println("Command doOnNext: ${it::class.java.simpleName}")
                    if (it.canExecute(params)) {
                        Logger.println("Executed")
                        it.execute(params)
                    } else Logger.println("NOOOOOOOOOOOOOOOOOOT Executed")

                    playerChangedUsecase.publishUpdate(it.playerColor)
                }
                .doAfterNext {
                    Logger.println("Command doAfterNext: ${it::class.java.simpleName}")
                    updatePlayerUsecase(
                            params = listOf(getCurrentPlayer(params).playerData),
                            onNext = {
                                Logger.println("CurrentPlayer updated: $it")
                                playerChangedUsecase.publishUpdate(it.first().playerColor)
                            },
                            onError = {
                                Logger.println("CurrentPlayer NOT updated: $it")
                            }
                    )
                }
    }

}