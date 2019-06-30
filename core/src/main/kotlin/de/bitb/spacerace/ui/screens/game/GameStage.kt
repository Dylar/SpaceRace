package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.objecthandling.*
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.model.player.PlayerImage
import javax.inject.Inject

class GameStage(val screen: GameScreen) : BaseStage(), DefaultFunction by DEFAULT {

    @Inject
    protected lateinit var fieldController: FieldController
    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
        addTestActor()
    }

    fun addEntitiesToMap() {
        addActor(fieldController.connections)
        fieldController.connections.zIndex = 0
        fieldController.fields.forEach { addActor(it.getGameImage()) }
        playerController.players.forEach { addActor(it.getGameImage()) }
    }

    override fun addActor(actor: Actor?) {
        super.addActor(actor)
        if (actor !is PlayerImage) {
            rearrangePlayer(actor!!) //TODO <-- still? <-- YES!
        }
    }

    private fun rearrangePlayer(actor: Actor) {
        if (playerController.players.isEmpty() || playerController.currentPlayer.getGameImage().zIndex == -1) {
            return
        }

        val actorIndex = actor.zIndex
//        Logger.println("ACTOR INDEX: $actorIndex")

        val indices: MutableList<Int> = ArrayList()
        playerController.players.forEach {
            indices.add(it.getGameImage().zIndex)
//            Logger.println("PLAYER INDEX PRE ${it.playerData.playerData.name}: ${it.getGameImage().zIndex}")
        }
        indices.reverse()

        actor.zIndex = indices.last()
        indices.removeAt(indices.lastIndex)
        indices.add(actorIndex)
        for (value in indices.withIndex()) {
            val player = playerController.players[value.index]
            player.getGameImage().zIndex = value.value + 1
        }
    }

    private fun addTestActor() {
        val target1 = Rectangle()
        val target2 = Rectangle(SCREEN_HEIGHT_HALF, SCREEN_HEIGHT_HALF, 10f, 10f)
        addActor(createTestActor(TextureAnimation(TextureCollection.infectedDirt), target1, target2))
//        addActor(createTestActor(TextureAnimation(TextureCollection.purpleCloud)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.purpleShuriken)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.bioCloud)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.flowerCloud)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.alienClaw)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyFlowerCloud)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyPurpleShuriken)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyFlameShuriken)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyShuriken)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyPoint)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyPinkShuriken)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinySpore)))
//        addActor(createTestActor(TextureAnimation(TextureCollection.tinyLight)))

//        addActor(createTestActor(SimpleAnimation()))
    }

    private fun createTestActor(animation: BaseAnimation, targetPosition1: Rectangle = Rectangle(), targetPosition2: Rectangle = targetPosition1): GameImage {
        var img = object : GameImage(animation),
                IRotatingImage by RotatingImage(),
                IMovingImage by MovingImage() {
            override var movingSpeed: Float = (MOVING_SPS * Math.random()).toFloat()

            init {
                setBounds(0f, 0f, FIELD_BORDER, FIELD_BORDER)

                patrollingBetweenPoints(this, targetPosition1, targetPosition2)
//                movingState = MovingState.ROTATE_POINT
//                setRotationRadius(width * 0.7)
            }

            override fun act(delta: Float) {
                super.act(delta)
                actRotation(this, targetPosition1, delta)
                targetPoint?.let { actMovingTo(delta, this, it) }
            }
        }
        img.setPosition(FIELD_BORDER / 2 * actors.size, -SCREEN_HEIGHT_HALF)
        targetPosition1.x = FIELD_BORDER / 2 * actors.size
        targetPosition1.y = -SCREEN_HEIGHT_HALF
//        CAMERA_TARGET = img
        return img
    }

}