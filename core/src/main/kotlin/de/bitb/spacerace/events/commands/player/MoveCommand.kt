package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.game.action.MoveUsecase
import javax.inject.Inject

class MoveCommand(val spaceField: SpaceField, playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var moveUsecase: MoveUsecase

    @Inject
    protected lateinit var playerController: PlayerController

    @Inject
    protected lateinit var fieldController: FieldController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean = true

    override fun execute() {
        moveUsecase.execute(
                params = playerData.playerColor to spaceField
        )
    }

}