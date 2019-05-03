package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ui.PlayerChangedUsecase
import javax.inject.Inject

class EndRoundCommand() : PhaseCommand(PlayerColor.NONE) {

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.isRoundEnd()
    }

    override fun execute(game: MainGame) {
        game.gameController.fieldController.harvestOres(game)
        val players = game.gameController.playerController.players

        game.gameController.fieldController.moveMovables(game)

        players.map { it.playerData }.forEach {
            //val saveData = playerData.copy() //TODO save insertNewPlayer for history
            it.nextRound()
        }

    }

}