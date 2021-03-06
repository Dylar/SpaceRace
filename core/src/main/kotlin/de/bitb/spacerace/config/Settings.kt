package de.bitb.spacerace.config

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.config.enums.GameSpeed
import de.bitb.spacerace.config.enums.GameType
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.game.VERSION_TEST
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.grafik.model.player.PlayerColor

var VERSION = "007"
val IS_TEST get() = VERSION == VERSION_TEST
const val MOVE_TIME: Double = 15.0
const val MOVING_SPS: Float = FIELD_BORDER
const val ROTATION_SPS = PLAYER_BORDER

var GAME_SPEED: GameSpeed = GameSpeed.GAME_SPEED_NORMAL

var GAME_TYPE: GameType = GameType.GAME_TYPE_ROUND

var SELECTED_MAP = "Unit Test"
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
val DEFAULT_SHIP = ItemType.SHIP_RAIDER

//GAME UI
const val MAX_ZOOM = 3.0f
const val MIN_ZOOM = .3f

const val FIELD_ROTATION = true
const val STAR_COUNT = 5

val COLOR_CONNECTED = Color(Color.GREEN).apply { a = 0.9f }
val COLOR_DISCONNECTED = Color(Color.RED).apply { a = 0.7f }

//val FONT_COLOR_TITLE = Color(.2f, .65f, .72f, 1f)
val FONT_COLOR_TITLE = Color(.2f, .85f, .85f, 1f)
val FONT_COLOR_BUTTON = Color.BLACK

//DEBUG
var DEBUG_TEST_FIELD = arrayListOf(FieldType.GOAL, FieldType.GIFT)
var DEBUG_GIFT_ITEMS: List<ItemInfo> = listOf()
var DEBUG_PLAYER_ITEMS: List<ItemInfo> = ItemInfo.getAll()//listOf(EngineIonInfo(), MineSlowInfo(), FuelExtraInfo())
//var DEBUG_PLAYER_ITEMS: List<ItemInfo> = ItemInfo.getAll().let { it.dropLast(it.size - 2) }
var DEBUG_PLAYER_ITEMS_COUNT = 6

var DEBUG_WIN_FIELD = true
const val DEBUG_FIELDS = false
const val DEBUG_LAYOUT = false
var DEBUG_CAMERA_TARGET: GameImage? = null

const val BITRISE_BORG = true