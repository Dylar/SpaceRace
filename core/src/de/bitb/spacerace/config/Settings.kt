package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.GameSpeed
import de.bitb.spacerace.config.enums.GameType
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.maps.MapCollection

const val MOVING_SPEED: Float = 100f
var GAME_SPEED: GameSpeed = GameSpeed.GAME_SPEED_NORMAL

var GAME_TYPE: GameType = GameType.GAME_TYPE_ROUND
val SELECTED_MAP = MapCollection.CIRCLEROAD

var LANGUAGE = Language.ENGLISH


//GAME UI
const val MAX_ZOOM = 5
const val MIN_ZOOM = 1

//DEBUG
var DEBUG_TEST_FIELD = FieldType.SHOP
const val DEBUG_ITEMS = 0

const val DEBUG_FIELDS = false
const val DEBUG_LAYOUT = false
