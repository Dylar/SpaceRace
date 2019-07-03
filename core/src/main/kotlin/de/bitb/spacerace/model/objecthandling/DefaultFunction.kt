package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerImage
import de.bitb.spacerace.model.player.PlayerItems
import de.bitb.spacerace.model.space.fields.SpaceField

val DEFAULT = object : DefaultFunction {}

interface DefaultFunction {

    fun getPlayer(playerController: PlayerController, playerColor: PlayerColor): Player {
        return playerController.getPlayer(playerColor)
    }

    fun getPlayerImage(playerController: PlayerController, playerColor: PlayerColor): PlayerImage {
        return getPlayer(playerController, playerColor).playerImage
    }

    fun getPlayerPosition(playerController: PlayerController, playerColor: PlayerColor): PositionData {
        return getPlayer(playerController, playerColor).gamePosition
    }

    fun getPlayerItems(playerController: PlayerController, playerColor: PlayerColor): PlayerItems {
        return getPlayer(playerController, playerColor).playerItems
    }

    fun getPlayerField(playerController: PlayerController, fieldController: FieldController, playerColor: PlayerColor): SpaceField {
        return fieldController.getField(getPlayerPosition(playerController, playerColor))
    }

    fun getItemField(fieldController: FieldController, item: Item): SpaceField {
        return fieldController.getField(item)
    }

    fun getDisplayImage(gameObject: GameObject, posX: Float = 0f, posY: Float = 0f, color: Color = Color(1f, 1f, 1f, 1f), actor: Actor = Actor()): Image {
        val image = object : Image(gameObject.getGameImage().animation.region) {
            override fun act(delta: Float) {
                super.act(delta)
                actor.act(delta)
            }
        }
        image.setOrigin(image.width / 2, image.height / 2)
        image.setPosition(posX - image.width / 2, posY - image.height / 2)
        image.color = color
        return image
    }

    fun getRunnableAction(runnable: Runnable): RunnableAction {
        val action = RunnableAction()
        action.runnable = runnable
        return action
    }

}