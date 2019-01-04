package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SLOW_MINE_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class SlowMine(owner: PlayerColor, price: Int) : DisposableItem(TextureCollection.slowMine, ItemCollection.SLOW_MINE, owner, ITEM_SLOW_MINE_TEXT, price), DiceModification {

    override fun canUse(game: MainGame): Boolean {
        return getPlayerData(game).phase.isMain()
    }

    override fun use(game: MainGame) {
        getPlayerData(game).diceModItems.add(this)
    }

    override fun dispose(game: MainGame) {
        getPlayerData(game).items.remove(this)
        getPlayerData(game).fieldPosition.disposedItems.add(this)
        getPlayerData(game).fieldPosition.update = true
    }

    override fun collect(game: MainGame) {
        owner = game.gameController.playerController.currentPlayer.playerData.playerColor
        use(game)
    }

    override fun getModification(): Float {
        return -0.1f
    }

}