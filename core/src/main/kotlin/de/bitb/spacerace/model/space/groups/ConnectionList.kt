package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.grafik.LineRenderer
import de.bitb.spacerace.model.space.fields.SpaceConnection

class ConnectionList(
        var graphicController: GraphicController,
        var playerController: PlayerController,
        val mutableList: MutableList<SpaceConnection> = ArrayList()
) : MutableList<SpaceConnection> by mutableList,
        Actor() {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(batch.projectionMatrix)

        forEach { con -> con.draw() }
        LineRenderer.endLine()
        batch.begin()
    }

    override fun clear() {
        mutableList.clear()
        super.clear()
    }
}