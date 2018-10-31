package de.bitb.spacerace.screens.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.LineRenderer
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.model.space.SpaceField
import de.bitb.spacerace.model.space.TestSpace


class GameScreen(game: BaseGame) : BaseScreen(game) {

    private var space: BaseSpace = TestSpace()

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(space, this)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(space, this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(space, this)
    }

    override fun renderGame(delta: Float) {
//        for (spaceGroup in space.fieldGroups) {
//            for (spaceField1 in space.fields) {
//                for (spaceField2 in spaceField1.connections) {
//                    drawConnection(spaceField1, spaceField2, Color.RED)
//                }
//            }
//        }
//
//        val ship = space.currentShip
//        if (!space.phase.isMain()) {
//            if (space.stepsLeft() == 0 && space.steps.size > 1) {
//                drawConnection(ship.fieldPosition, space.previousStep, Color.GREEN)
//            } else {
//                for (spaceField2 in ship.fieldPosition.connections) {
//                    drawConnection(ship.fieldPosition, spaceField2, Color.GREEN)
//                }
//            }
//        }

        val batch = gameStage.batch
        batch.begin()
        for (connection in space.connections) {
           connection.draw(batch, 1f, gameStage.camera.combined)
        }
        batch.end()
        super.renderGame(delta)
    }
//
//    fun drawConnection(spaceField1: SpaceField, spaceField2: SpaceField, color: Color) {
//        val start = Vector2(spaceField1.getAbsolutX(), spaceField1.getAbsolutY())
//        val end = Vector2(spaceField2.getAbsolutX(), spaceField2.getAbsolutY())
//        LineRenderer.drawDebugLine(start, end, 10, color, gameStage.camera.combined)
//    }

    override fun getCameraTarget(): BaseObject? {
        return space.currentShip
    }
}
