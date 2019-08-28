package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.LineRenderer
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceConnection

class ConnectionList(
        var graphicController: GraphicController,
        var playerController: PlayerController,
        val mutableList: MutableList<SpaceConnection> = ArrayList()
) : MutableList<SpaceConnection> by mutableList,
        Actor() {

    val connectedColor = Color(Color.GREEN)
    val disconnectedColor = Color(Color.RED)

    init {
        connectedColor.a = 0.9f
        disconnectedColor.a = 0.7f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(batch.projectionMatrix)

        forEach { con ->
            val currentPlayer = playerController.currentPlayerData
            with(graphicController.getPlayer(currentPlayer.playerColor).gamePosition) {
                con.draw(getColor(con, currentPlayer, this))
            }
        }
        LineRenderer.endLine()
        batch.begin()
    }

    fun getColor(con: SpaceConnection, playerData: PlayerData, positionData: PositionData): Color {
        val isConnected = con.isConnected(positionData)
        var color = disconnectedColor
        if (isConnected) {
            val canMove = playerData.canMove()
            if (canMove || playerData.phase.isMoving() && con.isConnected(playerData.previousStep)) {
                color = connectedColor
            }
        }

        return color
    }

    override fun clear() {
        mutableList.clear()
        super.clear()
    }
}