package de.bitb.spacerace.exceptions

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

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

class DiceFirstException(
        player: PlayerColor
) : NextPhaseException(player, "Need to dice first")

class StepsLeftException(
        player: PlayerColor,
        steps: Int
) : NextPhaseException(player, "$steps steps left")

sealed class ItemException(
        val itemInfo: ItemInfo,
        message: String
) : GameException(message)

class ItemNotImplementedException(
        itemInfo: ItemInfo
) : ItemException(itemInfo, "$itemInfo is not implemented")

class ItemNotFoundException(
        itemInfo: ItemInfo
) : ItemException(itemInfo, "$itemInfo is not found")
