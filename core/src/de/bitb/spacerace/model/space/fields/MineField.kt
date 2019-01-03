package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor

class MineField(fieldType: FieldType = FieldType.MINE) : SpaceField(fieldType) {

    var owner: PlayerColor = PlayerColor.NONE

    fun harvestOres(game: MainGame): Int {
        return if (owner == NONE) 0 else game.gameController.playerController.getPlayer(owner).addRandomWin()
    }

}