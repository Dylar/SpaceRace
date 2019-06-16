package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.usecase.game.PlayerTurnChangedUsecase
import javax.inject.Inject

class EndTurnCommand(playerData: PlayerData) : PhaseCommand(playerData) {

    @Inject
    protected lateinit var playerTurnChangedUsecase: PlayerTurnChangedUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        playerController
                .players
                .apply {
                    val oldPlayer = this[0]
                    var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
                    forEach {
                        it.getGameImage().zIndex = indexOld++
                    }
                    removeAt(0)
                    add(oldPlayer)

                    //TODO items in db
                    //oldPlayer.playerData.playerItems.removeUsedItems()

                }.also {
                    playerTurnChangedUsecase.publishUpdate(it.last().playerColor)
                }


    }

}