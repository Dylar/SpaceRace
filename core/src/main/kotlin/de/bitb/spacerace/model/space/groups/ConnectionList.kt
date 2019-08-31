package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.COLOR_DISCONNECTED
import de.bitb.spacerace.config.dimensions.Dimensions
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

    //    var reverse = false
    var index = 0

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(batch.projectionMatrix)

//        index += if (reverse) {
//            -1
//        } else {//TODO haha
//            +1
//        }
//        reverse = if (index == 0) false else if (index > 50) true else reverse

        forEach { (spaceField1, spaceField2, color) ->
            val start = Vector2(spaceField1.fieldImage.getCenterX(), spaceField1.fieldImage.getCenterY())
            val end = Vector2(spaceField2.fieldImage.getCenterX(), spaceField2.fieldImage.getCenterY())
            LineRenderer.drawDebugLine(start, end, Dimensions.GameDimensions.GAME_CONNECTIONS_WIDTH + index, color)
        }
        LineRenderer.endLine()
        batch.begin()
    }

    override fun clear() {
        mutableList.clear()
        super.clear()
    }
}