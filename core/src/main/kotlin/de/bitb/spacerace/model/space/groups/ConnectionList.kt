package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceConnection
import javax.inject.Inject

class ConnectionList(
//        var playerController: PlayerController,
        val mutableList: MutableList<SpaceConnection> = ArrayList())
    : MutableList<SpaceConnection> by mutableList,
        Actor() {

    val connectedColor = Color(Color.GREEN)
    val disconnectedColor = Color(Color.RED)

    @Inject
    lateinit var playerController: PlayerController

    init {
        connectedColor.a = 0.9f
        disconnectedColor.a = 0.7f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(batch.projectionMatrix)

        forEach { con ->
            val currentPlayer = playerController.currentPlayerData
            with(playerController.getPlayer(currentPlayer.playerColor).gamePosition) {
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
            val canMove = playerController.canMove(playerData)
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