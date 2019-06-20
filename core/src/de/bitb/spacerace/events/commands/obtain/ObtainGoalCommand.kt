package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class ObtainGoalCommand(playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var fieldController: FieldController

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.fieldController.currentGoal == getPlayerField(game.gameController.fieldController, playerData.playerColor)
    }

    override fun execute(game: MainGame) {
        playerData
                .apply {
                    credits += GOAL_CREDITS
                    victories++
                }
                .also {
                    updatePlayerUsecase.execute(listOf(it))
                    fieldController.setRandomGoal()
                }
    }

}