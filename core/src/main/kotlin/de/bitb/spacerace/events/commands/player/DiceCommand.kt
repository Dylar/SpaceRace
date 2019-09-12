package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.usecase.game.action.DiceUsecase
import javax.inject.Inject

class DiceCommand(
        playerData: PlayerData,
        val maxResult: Int = DICE_MAX
) : BaseCommand(playerData) {

    @Inject
    protected lateinit var diceUsecase: DiceUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        diceUsecase.execute(DONT_USE_THIS_PLAYER_DATA.playerColor to maxResult)
    }

}