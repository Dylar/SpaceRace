package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData

interface DefaultFunction {

    fun getPlayerData(game: MainGame, playerColor: PlayerColor): PlayerData {
        return game.gameController.playerController.getPlayer(playerColor).playerData
    }

    fun getDisplayImage(img: Texture): Image {
        return object : Image(img) {}
    }
}