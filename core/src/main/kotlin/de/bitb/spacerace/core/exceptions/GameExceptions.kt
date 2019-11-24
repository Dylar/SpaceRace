package de.bitb.spacerace.core.exceptions

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor

sealed class GameException(
        message: String
) : Throwable(message)

class SelectMorePlayerException() : GameException("Select more player")

class NotCurrentPlayerException(
        val player: PlayerColor
) : GameException("$player is not current player")

class WrongPhaseException(
        val player: PlayerColor,
        val phase: Set<Phase>
) : GameException("$player not in any phase $phase")

class RoundIsEndingException(
) : GameException("None player can change phase")

sealed class NotMovableException(
        val player: PlayerColor,
        val field: FieldData,
        reason: String
) : GameException("$player can not move to $field - $reason")

class FieldsNotConnectedException(
        player: PlayerColor,
        field: FieldData
) : NotMovableException(player, field, "not connected")

class NoStepsLeftException(
        player: PlayerColor,
        field: FieldData
) : NotMovableException(player, field, "no steps left")


sealed class NextPhaseException(
        val player: PlayerColor,
        reason: String
) : GameException("$player cannot change phase - $reason")

class MoreDiceException(
        player: PlayerColor,
        diced: Int,
        maxDice: Int
) : NextPhaseException(player, "Only diced $diced, can $maxDice")

class StepsLeftException(
        player: PlayerColor,
        steps: Int
) : NextPhaseException(player, "$steps steps left")

sealed class ItemException(
        val itemType: ItemType,
        message: String
) : GameException(message)

class ItemNotImplementedException(
        itemInfo: ItemInfo
) : ItemException(itemInfo.type, "$itemInfo is not implemented")

class ItemNotFoundException(
        itemInfo: ItemType
) : ItemException(itemInfo, "$itemInfo is not found")

class ItemNotUsableException(
        itemInfo: ItemInfo
) : ItemException(itemInfo.type, "$itemInfo is not usable")

class BuyItemLowCreditException(
        playerData: PlayerData,
        itemInfo: ItemInfo
) : ItemException(itemInfo.type, "${playerData.playerColor} has ${playerData.credits} credits, item cost: ${itemInfo.price}")

class PlayerNotOnShopException(
        playerData: PlayerData,
        itemInfo: ItemInfo
) : ItemException(itemInfo.type, "${playerData.playerColor} is not on field: ${playerData.positionField.target.fieldType}")
