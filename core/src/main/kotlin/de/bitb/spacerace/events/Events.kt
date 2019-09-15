package de.bitb.spacerace.events

import de.bitb.spacerace.model.player.PlayerColor

class OpenLoadGameEvent
class OpenDebugGuiEvent


class GameOverEvent(val winner: PlayerColor = PlayerColor.NONE)
class OpenEndRoundMenuEvent
class ObtainShopEvent(val playerColor: PlayerColor)


