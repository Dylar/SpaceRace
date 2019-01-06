package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.GameSpeed
import de.bitb.spacerace.config.enums.GameType
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.space.maps.MapCollection

const val MOVING_SPEED: Float = 100f
var DISPOSED_ITEM_SPEED = 8

var GAME_SPEED: GameSpeed = GameSpeed.GAME_SPEED_NORMAL

var GAME_TYPE: GameType = GameType.GAME_TYPE_ROUND
val SELECTED_MAP = MapCollection.TEST_MAP
var WIN_AMOUNT = 1
var WIN_CREDITS = 10000

var LANGUAGE = Language.ENGLISH


//GAME UI
const val MAX_ZOOM = 5
const val MIN_ZOOM = 1

//DEBUG
var DEBUG_TEST_FIELD = FieldType.MINE
var DEBUG_ITEM = ItemCollection.SLOW_MINE
const val DEBUG_ITEMS = 5

const val DEBUG_FIELDS = false
const val DEBUG_LAYOUT = false
