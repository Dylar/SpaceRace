package de.bitb.spacerace.model.space.control

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.space.fields.SpaceConnection

class ConnectionList(val space: GameController) : MutableList<SpaceConnection> by ArrayList(), Actor() {

    override fun draw(batch: Batch?, parentAlpha: Float) {
//        var matrix = stage.camera.combined
//        matrix = Matrix4(matrix)
//        LineRenderer.startLine(Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH, matrix)
//        for (i in 0 until size) {
////            get(i).draw(space.playerController.currentPlayer.playerData)
//        }
//        LineRenderer.endLine()
    }

    override fun clear() {
        super.clear()
    }
}