package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.*
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandPool.getCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.MapCreator
import de.bitb.spacerace.model.space.maps.createMap
import de.bitb.spacerace.model.space.maps.initDefaultMap

class ChangeLanguageCommand : BaseCommand() {
    companion object {
        fun get() = getCommand(ChangeLanguageCommand::class)
    }

    override fun execute() {
        LANGUAGE = when (LANGUAGE) {
            Language.ENGLISH -> Language.GERMAN
            Language.GERMAN -> Language.ENGLISH
        }
        reset()
    }
}

class ChangeDiceAmountCommand : BaseCommand() {
    companion object {
        fun get(amount: Int) = getCommand(ChangeDiceAmountCommand::class)
                .also { it.amount = amount }
    }

    private var amount: Int = 0
    override fun execute() {
        DICE_MAX += amount
        DICE_MAX = if (DICE_MAX < 1) 1 else DICE_MAX
        reset()
    }
}

class ChangeWinAmountCommand : BaseCommand() {
    companion object {
        fun get(amount: Int) = getCommand(ChangeWinAmountCommand::class)
                .also { it.amount = amount }
    }

    private var amount: Int = 0
    override fun execute() {
        WIN_AMOUNT += amount
        WIN_AMOUNT = if (WIN_AMOUNT < 1) 1 else WIN_AMOUNT
        reset()
    }
}

class SelectMapCommand : BaseCommand() {
    companion object {
        fun get(mapName: String) = getCommand(SelectMapCommand::class)
                .also { it.mapName = mapName }
    }

    private var mapName: String = MapCreator.TEST_MAP.name
    override fun execute() {
        SELECTED_MAP = mapName
        reset()
    }
}

class SelectPlayerCommand : BaseCommand() {
    companion object {
        fun get(playerColor: PlayerColor) = getCommand(SelectPlayerCommand::class)
                .also { it.playerColor = playerColor }
    }

    private var playerColor: PlayerColor = PlayerColor.NONE
    override fun execute() {
        if (!SELECTED_PLAYER.remove(playerColor)) {
            SELECTED_PLAYER.add(playerColor)
        }
        reset()
    }
}

class SelectTestFieldCommand() : BaseCommand() {
    companion object {
        fun get(testField: FieldType) = getCommand(SelectTestFieldCommand::class)
                .also { it.testField = testField }
    }

    private var testField: FieldType = FieldType.UNKNOWN
    override fun execute() {
        if (!DEBUG_TEST_FIELD.remove(testField)) {
            DEBUG_TEST_FIELD.add(testField)
        }
        reset()
    }
}