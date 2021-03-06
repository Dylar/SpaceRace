package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.math.Rectangle
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.model.objecthandling.BaseAnimation
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.objecthandling.TextureAnimation
import de.bitb.spacerace.grafik.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.grafik.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.grafik.model.objecthandling.rotating.RotatingImage
import javax.inject.Inject

class GameStage(
        val screen: GameScreen
) : BaseStage() {

    @Inject
    protected lateinit var playerController: PlayerController
    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
        addTestActor()
    }

    fun addEntitiesToMap() {
        addActor(graphicController.connectionGraphics)
        graphicController.connectionGraphics.zIndex = 0
        graphicController.fieldGraphics.values.forEach { addActor(it.getGameImage()) }
        graphicController.playerGraphics
                .map { it.getGameImage() }
                .reversed()
                .forEach { addActor(it) }


//        val fieldGraphic = graphicController.getFieldGraphic(graphicController.playerGraphics.first().gamePosition)
//        val itemGraphic = ItemInfo.MineSlowInfo().createGraphic()
//                .also { it.setPosition(100f, 100f) }
//        itemGraphic.itemImage.setRotating(itemGraphic, fieldGraphic.fieldImage, fieldGraphic.fieldImage.width * 0.7)
//        DEBUG_CAMERA_TARGET = itemGraphic.itemImage
////        fieldGraphic.addItem(itemGraphic as DisposableItemGraphic)
//        addActor(itemGraphic.itemImage)
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
        var img = object : GameImage(),
                IRotatingImage by RotatingImage(),
                IMovingImage by MovingImage() {
            override var movingSpeed: Float = (MOVING_SPS * Math.random()).toFloat()

            init {
                setBounds(0f, 0f, FIELD_BORDER, FIELD_BORDER)
                this.animation = animation
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