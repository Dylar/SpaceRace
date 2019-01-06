package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.player.Player

class GameStage(val screen: GameScreen) : BaseStage() {

    init {
        val controller = screen.game.gameController
        addEntitiesToMap(controller)
    }

    private fun addEntitiesToMap(gameController: GameController) {
        addActor(gameController.fieldController.connections)
        gameController.fieldController.fieldGroups.forEach { addActor(it) }
        gameController.playerController.players.forEach { addActor(it) }
    }

    override fun addActor(actor: Actor?) {
        super.addActor(actor)
        if (actor !is Player) {
            rearrangePlayer(actor!!)
        }
    }

    private fun rearrangePlayer(actor: Actor) {
        val playerController = screen.game.gameController.playerController
        if (playerController.players.isEmpty() || playerController.players[0].zIndex == -1) {
            return
        }

        val actorIndex = actor.zIndex
        Logger.println("ACTOR INDEX: $actorIndex")

        val indices: MutableList<Int> = ArrayList()
        playerController.players.forEach { it ->
            indices.add(it.zIndex)
            Logger.println("PLAYER INDEX PRE ${it.playerData.playerColor.name}: ${it.zIndex}")
        }
        indices.reverse()

        actor.zIndex = indices.last()
        indices.removeAt(indices.lastIndex)
        indices.add(actorIndex)
        for (value in indices.withIndex()) {
            val player = playerController.players[value.index]
            player.zIndex = value.value
            Logger.println("PLAYER INDEX POST ${player.playerData.playerColor.name}: ${actor.zIndex}")
        }
    }

}