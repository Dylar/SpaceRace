package de.bitb.spacerace.config

import de.bitb.spacerace.config.enums.GameSpeed
import de.bitb.spacerace.config.enums.GameType
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.space.maps.MapCollection

const val MOVING_SPEED: Float = 100f
var ROTATION_MOVING_SPEED = 8

var GAME_SPEED: GameSpeed = GameSpeed.GAME_SPEED_NORMAL

var GAME_TYPE: GameType = GameType.GAME_TYPE_ROUND
val SELECTED_MAP = MapCollection.TEST_MAP
var WIN_AMOUNT = 1
var DICE_MAX= 1
var WIN_CREDITS = 10000
var START_CREDITS = 10000

var LANGUAGE = Language.ENGLISH

//GAME

const val BLINKING_INTERVAL = 2f

//GAME UI
const val MAX_ZOOM = 5
const val MIN_ZOOM = 1

const val FIELD_ROTATION = true
const val STAR_COUNT = 10

//DEBUG
var DEBUG_TEST_FIELD = arrayListOf(FieldType.GOAL)
var DEBUG_ITEM = arrayListOf(ItemCollection.NONE)
const val DEBUG_ITEMS = 5

const val DEBUG_FIELDS = false
const val DEBUG_LAYOUT = false
