package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.player.PlayerItems
import de.bitb.spacerace.model.space.fields.SpaceField

interface DefaultFunction {

    fun getPlayer(game: MainGame, playerColor: PlayerColor): Player {
        return game.gameController.playerController.getPlayer(playerColor)
    }

    fun getCurrentPlayer(game: MainGame): Player {
        return game.gameController.playerController.currentPlayer
    }

    fun getPlayerPosition(game: MainGame, playerColor: PlayerColor): PositionData {
        return getPlayer(game, playerColor).positionData
    }

    fun getPlayerData(game: MainGame, playerColor: PlayerColor): PlayerData {
        return getPlayer(game, playerColor).playerData
    }

    fun getPlayerItems(game: MainGame, playerColor: PlayerColor): PlayerItems {
        return getPlayerData(game, playerColor).playerItems
    }

    fun getPlayerField(game: MainGame, playerColor: PlayerColor): SpaceField {
        return game.gameController.fieldController.getField(getPlayerPosition(game, playerColor))
    }

    fun getItemField(game: MainGame, item: Item): SpaceField {
        return game.gameController.fieldController.getField(item)
    }

    fun getDisplayImage(img: Texture, posX: Float = 0f, posY: Float = 0f, color: Color = Color(1f, 1f, 1f, 1f), actor: Actor = Actor()): Image {
        val image = object : Image(img) {
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