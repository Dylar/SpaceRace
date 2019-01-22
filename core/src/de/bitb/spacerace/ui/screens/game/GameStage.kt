package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.CAMERA_TARGET
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH_HALF
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.background.StarImage
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.TextureAnimation
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage
import de.bitb.spacerace.model.player.PlayerImage

class GameStage(val screen: GameScreen) : BaseStage() {

    init {
        val controller = screen.game.gameController
        addTestActor()
        addEntitiesToMap(controller)
    }

    private fun addEntitiesToMap(gameController: GameController) {
        addActor(gameController.fieldController.connections)
        gameController.fieldController.fields.forEach { addActor(it.getGameImage()) }
        gameController.playerController.players.forEach { addActor(it.getGameImage()) }
    }

    override fun addActor(actor: Actor?) {
        super.addActor(actor)
        if (actor !is PlayerImage) {
            rearrangePlayer(actor!!) //TODO <-- still?
        }
    }

    private fun rearrangePlayer(actor: Actor) {
        val playerController = screen.game.gameController.playerController
        if (playerController.players.isEmpty() || playerController.players[0].getGameImage().zIndex == -1) {
            return
        }

        val actorIndex = actor.zIndex
        Logger.println("ACTOR INDEX: $actorIndex")

        val indices: MutableList<Int> = ArrayList()
        playerController.players.forEach { it ->
            indices.add(it.getGameImage().zIndex)
            Logger.println("PLAYER INDEX PRE ${it.playerData.playerColor.name}: ${it.getGameImage().zIndex}")
        }
        indices.reverse()

        actor.zIndex = indices.last()
        indices.removeAt(indices.lastIndex)
        indices.add(actorIndex)
        for (value in indices.withIndex()) {
            val player = playerController.players[value.index]
            player.getGameImage().zIndex = value.value
            Logger.println("PLAYER INDEX POST ${player.playerData.playerColor.name}: ${actor.zIndex}")
        }
    }

    private fun addTestActor() {
        addActor(createTestActor(TextureCollection.infectedDirt))
        addActor(createTestActor(TextureCollection.purpleCloud))
        addActor(createTestActor(TextureCollection.purpleShuriken))
        addActor(createTestActor(TextureCollection.bioCloud))
        addActor(createTestActor(TextureCollection.flowerCloud))
        addActor(createTestActor(TextureCollection.alienClaw))
        addActor(createTestActor(TextureCollection.tinyFlowerCloud))
        addActor(createTestActor(TextureCollection.tinyPurpleShuriken))
        addActor(createTestActor(TextureCollection.tinyFlameShuriken))
        addActor(createTestActor(TextureCollection.tinyShuriken))
        addActor(createTestActor(TextureCollection.tinyPoint))
        addActor(createTestActor(TextureCollection.tinyPinkShuriken))
        addActor(createTestActor(TextureCollection.tinySpore))
        addActor(createTestActor(TextureCollection.tinyLight))
    }

    private fun createTestMovingActor(texture: Texture): GameImage {
//        return FallingStar(screen).getGameImage()
        return StarImage(texture, screen)
    }

    private fun createTestActor(texture: Texture, rotationPoint: Rectangle = Rectangle()): GameImage {
        var img = object : GameImage(TextureAnimation(texture)), IRotatingImage by RotatingImage() {
            override var movingSpeed: Float = MOVING_SPS

            init {
                setBounds(0f, 0f, Dimensions.GameDimensions.FIELD_BORDER, Dimensions.GameDimensions.FIELD_BORDER)
            }

            override fun act(delta: Float) {
                super.act(delta)
                actRotation(this, rotationPoint, delta)
            }
        }
        img.setPosition(FIELD_BORDER / 2 * actors.size, SCREEN_HEIGHT_HALF)
        CAMERA_TARGET = img
        return img
    }
}