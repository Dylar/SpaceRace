package de.bitb.spacerace.core.events

import de.bitb.spacerace.grafik.model.player.PlayerColor

class OpenLoadGameEvent
class OpenDebugGuiEvent


class GameOverEvent(val winner: PlayerColor = PlayerColor.NONE)
class OpenEndRoundMenuEvent
class ObtainShopEvent(val playerColor: PlayerColor)

