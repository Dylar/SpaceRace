package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData

interface DefaultFunction {

    fun getPlayerData(game: MainGame, playerColor: PlayerColor): PlayerData {
        return game.gameController.playerController.getPlayer(playerColor).playerData
    }

    fun getDisplayImage(img: Texture, posX: Float = 0f, posY: Float = 0f, color: Color = Color(1f, 1f, 1f, 1f)): Image {
        val image = object : Image(img) {}
        image.setPosition(posX, posY)
        image.color = color
        return image
    }
}