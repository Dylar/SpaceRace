package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.Single

object ItemHelper {

    fun getItem(playerDataSource: PlayerDataSource,
                playerColor: PlayerColor,
                itemInfo: ItemInfo,
                getItemContainer: (PlayerData) -> List<ItemData>
    ): Single<ItemData> =
            playerDataSource.getByColor(playerColor)
                    .map { it.first() }
                    .map { playerData ->
                        getItemContainer(playerData)
                                .firstOrNull { it.itemInfo.name == itemInfo.name }
                                ?: throw ItemNotFoundException(itemInfo)
                    }
}