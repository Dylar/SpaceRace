package de.bitb.spacerace.grafik.model.space.groups

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.grafik.LineRenderer
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic

class ConnectionList(
        private val mutableList: MutableList<ConnectionGraphic> = ArrayList()
) : MutableList<ConnectionGraphic> by mutableList,
        Actor() {

    //    var reverse = false
    var index = 0

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        LineRenderer.startLine(batch.projectionMatrix)

//        index += if (reverse) {
//            -1
//        } else {//TODO pulsar lines
//            +1
//        }
//        reverse = if (index == 0) false else if (index > 50) true else reverse

        forEach { (spaceField1, spaceField2, color) ->
            val pos1 = spaceField1.fieldImage
            val pos2 = spaceField2.fieldImage
            val start = Vector2(pos1.getCenterX(), pos1.getCenterY())
            val end = Vector2(pos2.getCenterX(), pos2.getCenterY())
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