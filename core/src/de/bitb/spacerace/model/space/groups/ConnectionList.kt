package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.model.space.fields.SpaceConnection

class ConnectionList(val playerController: PlayerController) : MutableList<SpaceConnection> by ArrayList(), Actor() {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH, batch.projectionMatrix)
        for (i in 0 until size) {
            get(i).draw(playerController.currentPlayer.playerData)
        }
        LineRenderer.endLine()
        batch.begin()
    }

    override fun clear() {
        super.clear()
    }
}