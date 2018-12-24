package de.bitb.spacerace.model.space.control

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceConnection

class ConnectionList(val gameController: GameController) : MutableList<SpaceConnection> by ArrayList(), Actor() {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        val matrix = Matrix4(batch.projectionMatrix)
        LineRenderer.startLine(Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH, matrix)
        for (i in 0 until size) {
            get(i).draw(gameController.playerController.currentPlayer.playerData)
        }
        LineRenderer.endLine()
        batch.begin()
    }

    fun draw(playerData: PlayerData) {
        for (i in 0 until size) {
            get(i).draw(playerData)
        }
    }

    override fun clear() {
        super.clear()
    }
}