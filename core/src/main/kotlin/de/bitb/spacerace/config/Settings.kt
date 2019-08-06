package de.bitb.spacerace.config

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.config.enums.GameSpeed
import de.bitb.spacerace.config.enums.GameType
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.MapCollection


const val MOVE_TIME: Double = 15.0
const val MOVING_SPS: Float = FIELD_BORDER
const val ROTATION_SPS = PLAYER_BORDER

var GAME_SPEED: GameSpeed = GameSpeed.GAME_SPEED_NORMAL

var GAME_TYPE: GameType = GameType.GAME_TYPE_ROUND
val SELECTED_MAP = MapCollection.TEST_MAP

val SELECTED_PLAYER: MutableList<PlayerColor> = mutableListOf(PlayerColor.ORANGE, PlayerColor.GREEN)
var WIN_AMOUNT = 1L
var DICE_MAX = 1
const val GOAL_CREDITS = 10000
const val START_CREDITS = 10000
const val CREDITS_WIN_AMOUNT = 1000
const val CREDITS_LOSE_AMOUNT = 500

var LANGUAGE = Language.ENGLISH

//GAME

const val BLINKING_INTERVAL = 2f
const val ITEM_SELL_MOD = 0.7

//GAME UI
const val MAX_ZOOM = 5.0
const val MIN_ZOOM = 1.0

const val FIELD_ROTATION = true
const val STAR_COUNT = 5

//DEBUG
//var PRESELECTED_PLAYER = arrayListOf()
var DEBUG_TEST_FIELD = arrayListOf(FieldType.GOAL)
var DEBUG_ITEM = arrayListOf(ItemCollection.NONE)
const val DEBUG_ITEMS = 0

const val DEBUG_WIN_FIELD = true
const val DEBUG_FIELDS = false
const val DEBUG_LAYOUT = false
var CAMERA_TARGET: GameImage? = null
