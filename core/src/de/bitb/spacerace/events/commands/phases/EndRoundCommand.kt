package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class EndRoundCommand() : PhaseCommand(Player.NONE.playerData) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    @Inject
    protected lateinit var fieldController: FieldController

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return playerController.isRoundEnd()
    }

    override fun execute(game: MainGame) {
        fieldController.harvestOres(game)

        fieldController.moveMovables(game)

        playerController
                .players
                .map { it.playerData }
                .apply { forEach { it.nextRound() } }
                .also { updatePlayerUsecase.execute(it) }
    }

}