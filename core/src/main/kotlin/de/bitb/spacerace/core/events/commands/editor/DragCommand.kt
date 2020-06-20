package de.bitb.spacerace.core.events.commands.editor

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool.getCommand
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.usecase.game.action.MoveResult
import javax.inject.Inject

class DragCommand : BaseCommand() {
    companion object {
        fun get(entity: GameImage) = getCommand(DragCommand::class)
                .also {
                    it.entity = entity
                }
    }

    @Inject
    protected lateinit var editorGloc: EditorGloc

    private lateinit var entity: GameImage

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {

//        moveUsecase.getResult(
//                params = player to targetPosition,
//                onSuccess = {
//                    setGraphics(it)
//                    reset()
//                },
//                onError = { reset() }
//        ).addDisposable()
    }

    private fun setGraphics(moveResult: MoveResult) {
//        graphicController.movePlayer(moveResult)
//        graphicController.setConnectionColor(moveResult.player, moveResult.targetableFields)
    }
}